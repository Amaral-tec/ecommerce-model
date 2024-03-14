package br.com.amaral.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JunoBankSlipReturnDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private JunoEmbeddedDTO _embedded = new JunoEmbeddedDTO();

	private List<JunoLinksDTO> _links = new ArrayList<>();

	public JunoEmbeddedDTO get_embedded() {
		return _embedded;
	}

	public void set_embedded(JunoEmbeddedDTO _embedded) {
		this._embedded = _embedded;
	}

	public List<JunoLinksDTO> get_links() {
		return _links;
	}

	public void set_links(List<JunoLinksDTO> _links) {
		this._links = _links;
	}
	

}
