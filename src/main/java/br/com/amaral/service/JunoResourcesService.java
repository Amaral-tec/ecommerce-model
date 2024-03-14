package br.com.amaral.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.amaral.constant.APITokens;
import br.com.amaral.model.JunoAccessToken;
import br.com.amaral.model.JunoBankSlip;
import br.com.amaral.model.Sale;
import br.com.amaral.model.dto.JunoAPIChargeDTO;
import br.com.amaral.model.dto.JunoBankSlipDataDTO;
import br.com.amaral.model.dto.JunoBankSlipRequestDTO;
import br.com.amaral.model.dto.JunoBankSlipReturnDTO;
import br.com.amaral.repository.IJunoAccesTokenRepository;
import br.com.amaral.repository.IJunoBankSlipRepository;
import br.com.amaral.repository.ISaleRepository;

@Service
public class JunoResourcesService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private JunoAccessTokenService junoAccessTokenService;

	@Autowired
	private IJunoAccesTokenRepository junoAccesTokenRepository;

	@Autowired
	private ISaleRepository saleRepository;

	@Autowired
	private IJunoBankSlipRepository junoBankSlipRepository;

	public String generateInvoiceKey() throws Exception {

		JunoAccessToken accessToken = this.getJunoToken();

		Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
		WebResource webResource = client.resource("https://api.juno.com.br/pix/keys");

		ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
				.header("Content-Type", "application/json").header("X-API-Version", 2)
				.header("X-Resource-Token", APITokens.TOKEN_PRIVATE_JUNO)
				.header("Authorization", "Bearer " + accessToken.getAccess_token())
				.post(ClientResponse.class, "{ \"type\": \"RANDOM_KEY\" }");

		return clientResponse.getEntity(String.class);

	}

	public JunoAccessToken getJunoToken() throws Exception {

		JunoAccessToken accessToken = junoAccessTokenService.getActiveToken();

		if (accessToken == null || (accessToken != null && accessToken.isExpired())) {

			String clienteID = "vi7QZerW09C8JG1o";
			String secretID = "$A_+&ksH}&+2<3VM]1MZqc,F_xif_-Dc";

			Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();

			WebResource webResource = client
					.resource("https://api.juno.com.br/authorization-server/oauth/token?grant_type=client_credentials");

			String basicKey = clienteID + ":" + secretID;
			String authentication_token = DatatypeConverter.printBase64Binary(basicKey.getBytes());

			ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.type(MediaType.APPLICATION_FORM_URLENCODED)
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Authorization", "Basic " + authentication_token).post(ClientResponse.class);

			if (clientResponse.getStatus() == 200) {
				junoAccesTokenRepository.deleteAll();
				junoAccesTokenRepository.flush();

				JunoAccessToken newAccessToken = clientResponse.getEntity(JunoAccessToken.class);
				newAccessToken.setToken_access_base64(authentication_token);

				newAccessToken = junoAccesTokenRepository.saveAndFlush(newAccessToken);

				return newAccessToken;
			} else {
				return null;
			}
		} else {
			return accessToken;
		}
	}

	public String generateCharge(JunoBankSlipRequestDTO bankSlipRequest) throws Exception {

		Sale sale = saleRepository.findById(bankSlipRequest.getSaleId()).get();

		JunoAPIChargeDTO charge = new JunoAPIChargeDTO();

		charge.getCharge().setPixKey(APITokens.JUNO_INVOICE_KEY);
		charge.getCharge().setDescription(bankSlipRequest.getDescription());
		charge.getCharge().setAmount(Float.valueOf(bankSlipRequest.getTotalAmount()));
		charge.getCharge().setInstallments(Integer.parseInt(bankSlipRequest.getInstallments()));

		Calendar dueDate = Calendar.getInstance();
		dueDate.add(Calendar.DAY_OF_MONTH, 7);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
		charge.getCharge().setDueDate(dateFormat.format(dueDate.getTime()));

		charge.getCharge().setFine(BigDecimal.valueOf(1.00));
		charge.getCharge().setInterest(BigDecimal.valueOf(1.00));
		charge.getCharge().setMaxOverdueDays(10);
		charge.getCharge().getPaymentTypes().add("BOLETO_PIX");

		charge.getBilling().setName(bankSlipRequest.getPayerName());
		charge.getBilling().setDocument(bankSlipRequest.getPayerCpfCnpj());
		charge.getBilling().setEmail(bankSlipRequest.getEmail());
		charge.getBilling().setPhone(bankSlipRequest.getPayerPhone());

		JunoAccessToken accessToken = this.getJunoToken();
		if (accessToken != null) {

			Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
			WebResource webResource = client.resource("https://api.juno.com.br/charges");

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(charge);

			ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
					.header("Content-Type", "application/json;charset=UTF-8").header("X-API-Version", 2)
					.header("X-Resource-Token", APITokens.TOKEN_PRIVATE_JUNO)
					.header("Authorization", "Bearer " + accessToken.getAccess_token())
					.post(ClientResponse.class, json);

			String result = clientResponse.getEntity(String.class);

			if (clientResponse.getStatus() == 200) {

				clientResponse.close();
				objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

				JunoBankSlipReturnDTO jsonReturnObject = objectMapper.readValue(result,
						new TypeReference<JunoBankSlipReturnDTO>() {
						});

				int recurrence = 1;

				List<JunoBankSlip> bankSlipList = new ArrayList<>();

				for (JunoBankSlipDataDTO data : jsonReturnObject.get_embedded().getCharges()) {

					JunoBankSlip bankSlip = new JunoBankSlip();

					bankSlip.setLegalEntity(sale.getLegalEntity());
					bankSlip.setSale(sale);
					bankSlip.setCode(data.getCode());
					bankSlip.setLink(data.getLink());
					bankSlip.setDueDate(new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("yyyy-MM-dd").parse(data.getDueDate())));
					bankSlip.setCheckoutUrl(data.getCheckoutUrl());
					bankSlip.setAmount(new BigDecimal(data.getAmount()));
					bankSlip.setIdChrBankSlip(data.getId());
					bankSlip.setInstallmentLink(data.getInstallmentLink());
					bankSlip.setIdPix(data.getPix().getId());
					bankSlip.setPayloadInBase64(data.getPix().getPayloadInBase64());
					bankSlip.setImageInBase64(data.getPix().getImageInBase64());
					bankSlip.setRecurrence(recurrence);

					bankSlipList.add(bankSlip);
					recurrence++;
				}

				junoBankSlipRepository.saveAllAndFlush(bankSlipList);

				return bankSlipList.get(0).getLink();

			} else {
				return result;
			}

		} else {
			return "There is no access key for the API";
		}
	}

	public String cancelChance(String code) throws Exception {

		JunoAccessToken accessToken = this.getJunoToken();

		Client client = new HostIgnoringClient("https://api.juno.com.br/").hostIgnoringClient();
		WebResource webResource = client.resource("https://api.juno.com.br/charges/" + code + "/cancelation");

		ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON).header("X-Api-Version", 2)
				.header("X-Resource-Token", APITokens.TOKEN_PRIVATE_JUNO)
				.header("Authorization", "Bearer " + accessToken.getAccess_token()).put(ClientResponse.class);

		if (clientResponse.getStatus() == 204) {
			return "Cancelado com sucesso";
		}

		return clientResponse.getEntity(String.class);

	}

}
