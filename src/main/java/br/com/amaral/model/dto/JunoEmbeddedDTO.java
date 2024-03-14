package br.com.amaral.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JunoEmbeddedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<JunoBankSlipDataDTO> charges = new ArrayList<>();

	public List<JunoBankSlipDataDTO> getCharges() {
		return charges;
	}

	public void setCharges(List<JunoBankSlipDataDTO> charges) {
		this.charges = charges;
	}

}
