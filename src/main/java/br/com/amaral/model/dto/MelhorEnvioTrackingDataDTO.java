package br.com.amaral.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MelhorEnvio API
 */
public class MelhorEnvioTrackingDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String service;
	private String agency;

	private MelhorEnvioTrackingDataFromDTO from = new MelhorEnvioTrackingDataFromDTO();

	private MelhorEnvioTrackingDataToDTO to = new MelhorEnvioTrackingDataToDTO();

	private List<MelhorEnvioTrackingDataProductsDTO> products = new ArrayList<>();

	private List<MelhorEnvioTrackingDataVolumesDTO> volumes = new ArrayList<>();
	
	private MelhorEnvioTrackingDataOptionsDTO options = new MelhorEnvioTrackingDataOptionsDTO();

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public MelhorEnvioTrackingDataFromDTO getFrom() {
		return from;
	}

	public void setFrom(MelhorEnvioTrackingDataFromDTO from) {
		this.from = from;
	}

	public MelhorEnvioTrackingDataToDTO getTo() {
		return to;
	}

	public void setTo(MelhorEnvioTrackingDataToDTO to) {
		this.to = to;
	}

	public List<MelhorEnvioTrackingDataProductsDTO> getProducts() {
		return products;
	}

	public void setProducts(List<MelhorEnvioTrackingDataProductsDTO> products) {
		this.products = products;
	}

	public List<MelhorEnvioTrackingDataVolumesDTO> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<MelhorEnvioTrackingDataVolumesDTO> volumes) {
		this.volumes = volumes;
	}

	public MelhorEnvioTrackingDataOptionsDTO getOptions() {
		return options;
	}

	public void setOptions(MelhorEnvioTrackingDataOptionsDTO options) {
		this.options = options;
	}

}
