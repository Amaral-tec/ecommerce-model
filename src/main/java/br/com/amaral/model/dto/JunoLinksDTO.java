package br.com.amaral.model.dto;

import java.io.Serializable;

public class JunoLinksDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JunoSelfDTO self = new JunoSelfDTO();

	public JunoSelfDTO getSelf() {
		return self;
	}

	public void setSelf(JunoSelfDTO self) {
		this.self = self;
	}
	
}
