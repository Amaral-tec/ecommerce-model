package br.com.amaral.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.amaral.ExceptionProject;
import br.com.amaral.constant.APITokens;
import br.com.amaral.model.ProductSold;
import br.com.amaral.model.Sale;
import br.com.amaral.model.dto.MelhorEnvioShippingCompanyDTO;
import br.com.amaral.model.dto.MelhorEnvioShippingDTO;
import br.com.amaral.model.dto.MelhorEnvioTrackingDataDTO;
import br.com.amaral.model.dto.MelhorEnvioTrackingDataProductsDTO;
import br.com.amaral.model.dto.MelhorEnvioTrackingDataTagsDTO;
import br.com.amaral.model.dto.MelhorEnvioTrackingDataVolumesDTO;
import br.com.amaral.repository.ISaleRepository;
import okhttp3.OkHttpClient;

/**
 * Link https://docs.melhorenvio.com.br/docs
 */
@RestController
public class MelhorEnvioController {

	@Autowired
	private ISaleRepository saleRepository;
	
	@ResponseBody
	@PostMapping(value = "**/get-shipping-data")
	public ResponseEntity<List<MelhorEnvioShippingCompanyDTO>> getShippingData(
			@RequestBody @Valid MelhorEnvioShippingDTO quoteShippingDTO) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(quoteShippingDTO);

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/calculate").method("POST", body)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response response = client.newCall(request).execute();

		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

		Iterator<JsonNode> iterator = jsonNode.iterator();

		List<MelhorEnvioShippingCompanyDTO> list = new ArrayList<>();

		while (iterator.hasNext()) {
			JsonNode node = iterator.next();

			MelhorEnvioShippingCompanyDTO shippingCompanyDTO = new MelhorEnvioShippingCompanyDTO();

			if (node.get("id") != null) {
				shippingCompanyDTO.setId(node.get("id").asText());
			}

			if (node.get("name") != null) {
				shippingCompanyDTO.setName(node.get("name").asText());
			}

			if (node.get("price") != null) {
				shippingCompanyDTO.setPrice(node.get("price").asText());
			}

			if (node.get("company") != null) {
				shippingCompanyDTO.setCompany(node.get("company").get("name").asText());
				shippingCompanyDTO.setPicture(node.get("company").get("picture").asText());
			}

			if (shippingCompanyDTO.isOK()) {
				list.add(shippingCompanyDTO);
			}
		}

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/get-tracking-number/{saleId}")
	public ResponseEntity<String> getTrackingNumber(@PathVariable Long saleId) throws ExceptionProject, IOException {

		Sale sale = saleRepository.findById(saleId).orElse(null);

		if (sale == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + saleId);
		}

		MelhorEnvioTrackingDataDTO tracking = new MelhorEnvioTrackingDataDTO();

		tracking.setService(sale.getShippingCompany());
		tracking.setAgency("49");
		tracking.getFrom().setName(sale.getLegalEntity().getName());
		tracking.getFrom().setPhone(sale.getLegalEntity().getPhone());
		tracking.getFrom().setEmail(sale.getLegalEntity().getEmail());
		tracking.getFrom().setCompany_document(sale.getLegalEntity().getCnpj());
		tracking.getFrom().setState_register(sale.getLegalEntity().getStateTaxID());
		tracking.getFrom().setAddress(sale.getLegalEntity().getAddresses().get(0).getStreetAddress());
		tracking.getFrom().setComplement(sale.getLegalEntity().getAddresses().get(0).getComplement());
		tracking.getFrom().setNumber(sale.getLegalEntity().getAddresses().get(0).getNumber());
		tracking.getFrom().setDistrict(sale.getLegalEntity().getAddresses().get(0).getDistrict());
		tracking.getFrom().setCity(sale.getLegalEntity().getAddresses().get(0).getCity());
		tracking.getFrom().setCountry_id(sale.getLegalEntity().getAddresses().get(0).getCountryId());
		tracking.getFrom().setPostal_code(sale.getLegalEntity().getAddresses().get(0).getPostalCode());
		tracking.getFrom().setNote("Null");

		tracking.getTo().setName(sale.getIndividual().getName());
		tracking.getTo().setPhone(sale.getIndividual().getPhone());
		tracking.getTo().setEmail(sale.getIndividual().getEmail());
		tracking.getTo().setDocument(sale.getIndividual().getCpf());
		tracking.getTo().setAddress(sale.getIndividual().shippingAddress().getStreetAddress());
		tracking.getTo().setComplement(sale.getIndividual().shippingAddress().getComplement());
		tracking.getTo().setNumber(sale.getIndividual().shippingAddress().getNumber());
		tracking.getTo().setDistrict(sale.getIndividual().shippingAddress().getDistrict());
		tracking.getTo().setCity(sale.getIndividual().shippingAddress().getCity());
		tracking.getTo().setState_abbr(sale.getIndividual().shippingAddress().getState());
		tracking.getTo().setCountry_id(sale.getIndividual().shippingAddress().getCountryId());
		tracking.getTo().setPostal_code(sale.getIndividual().shippingAddress().getPostalCode());
		tracking.getTo().setNote("Null");

		List<MelhorEnvioTrackingDataProductsDTO> products = new ArrayList<>();

		for (ProductSold productSold : sale.getProductsSold()) {

			MelhorEnvioTrackingDataProductsDTO productDTO = new MelhorEnvioTrackingDataProductsDTO();

			productDTO.setName(productSold.getProduct().getName());
			productDTO.setQuantity(productSold.getQuantity().toString());
			productDTO.setUnitary_value("" + productSold.getProduct().getSellingPrice().doubleValue());

			products.add(productDTO);
		}

		tracking.setProducts(products);

		List<MelhorEnvioTrackingDataVolumesDTO> volumes = new ArrayList<>();

		for (ProductSold productSold : sale.getProductsSold()) {

			MelhorEnvioTrackingDataVolumesDTO volumesDTO = new MelhorEnvioTrackingDataVolumesDTO();

			volumesDTO.setHeight(productSold.getProduct().getHeight().toString());
			volumesDTO.setLength(productSold.getProduct().getLength().toString());
			volumesDTO.setWeight(productSold.getProduct().getWeight().toString());
			volumesDTO.setWidth(productSold.getProduct().getWidth().toString());

			volumes.add(volumesDTO);
		}

		tracking.setVolumes(volumes);

		tracking.getOptions().setInsurance_value("" + sale.getTotalAmount().doubleValue());
		tracking.getOptions().setReceipt(false);
		tracking.getOptions().setOwn_hand(false);
		tracking.getOptions().setReverse(false);
		tracking.getOptions().setNon_commercial(false);
		tracking.getOptions().getInvoice().setKey(sale.getSalesInvoice().getKey());
		tracking.getOptions().setPlatform(sale.getLegalEntity().getTradeName());

		MelhorEnvioTrackingDataTagsDTO tagDTO = new MelhorEnvioTrackingDataTagsDTO();
		tagDTO.setTag("Order identification on the platform: " + sale.getId());
		tagDTO.setUrl(null);
		tracking.getOptions().getTags().add(tagDTO);

		String sendJson = new ObjectMapper().writeValueAsString(tracking);

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, sendJson);
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/cart").method("POST", body)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response response = client.newCall(request).execute();

		String jsonResponse = response.body().string();
		if (jsonResponse.contains("error")) {
			throw new ExceptionProject(jsonResponse);
		}

		JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);

		Iterator<JsonNode> iterator = jsonNode.iterator();

		String trackingNumber = "";

		while (iterator.hasNext()) {
			JsonNode node = iterator.next();
			if (node.get("id") != null) {
				trackingNumber = node.get("id").asText();
			} else {
				trackingNumber = node.asText();
			}
			break;
		}

		// em caso de erro, usar jdbcTemplate
		saleRepository.updateTrackingNumber(trackingNumber, sale.getId());

		OkHttpClient clientCheckout = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaTypeCheckout = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody bodyCheckout = okhttp3.RequestBody.create(mediaTypeCheckout,
				"{\n    \"orders\": [\n        \"" + trackingNumber + "\"\n    ]\n}");
		okhttp3.Request requestCheckout = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/checkout").method("POST", bodyCheckout)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response responseCheckout = clientCheckout.newCall(requestCheckout).execute();

		if (!responseCheckout.isSuccessful()) {
			return new ResponseEntity<>("Unable to purchase shipping service", HttpStatus.OK);
		}

		OkHttpClient clientGenerate = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaTypeGenerate = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody bodyGenerate = okhttp3.RequestBody.create(mediaTypeGenerate,
				"{\n    \"orders\":[\n        \"" + trackingNumber + "\"\n    ]\n}");
		okhttp3.Request requestGenerate = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/generate").method("POST", bodyGenerate)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response responseGenerate = clientGenerate.newCall(requestGenerate).execute();

		if (!responseGenerate.isSuccessful()) {
			return new ResponseEntity<>("It is not possible to generate proof of shipping service", HttpStatus.OK);
		}

		OkHttpClient clientPrint = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaTypePrint = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody bodyPrint = okhttp3.RequestBody.create(mediaTypePrint,
				"{\n    \"mode\": \"private\",\n    \"orders\": [\n        \"" + trackingNumber + "\"\n    ]\n}");
		okhttp3.Request requestPrint = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/print").method("POST", bodyPrint)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response responsePrint = clientPrint.newCall(requestPrint).execute();

		if (!responsePrint.isSuccessful()) {
			return new ResponseEntity<>("Unable to print shipping service receipt.", HttpStatus.OK);
		}

		String labelUrl = responsePrint.body().string();
		saleRepository.updateLabelURL(labelUrl, sale.getId());

		return new ResponseEntity<>("Sucess", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/cancel-shipment/{trackingNumber}/{reason_id}/{description}")
	public ResponseEntity<String> cancelShipment(@PathVariable String trackingNumber, @PathVariable String reason_id,
			@PathVariable String description) throws IOException {

		OkHttpClient client = new OkHttpClient();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType,
				"{\n    \"order\": {\n        \"id\": \"" + trackingNumber + "\",\n        \"reason_id\": \""
						+ reason_id + "\",\n        \"description\": \"" + description + "\"\n    }\n}");
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url(APITokens.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/cancel").post(body)
				.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + APITokens.TOKEN_MELHOR_ENVIO_SAND_BOX)
				.addHeader("User-Agent", "amaral_adm@hotmail.com").build();

		okhttp3.Response response = client.newCall(request).execute();

		return new ResponseEntity<>(response.body().string(), HttpStatus.OK);
	}
}
