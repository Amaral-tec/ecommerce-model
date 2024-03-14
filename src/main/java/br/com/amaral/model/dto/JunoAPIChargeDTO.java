package br.com.amaral.model.dto;

import java.io.Serializable;

public class JunoAPIChargeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private JunoChargeDTO charge = new JunoChargeDTO();
	private JunoBillingDTO billing = new JunoBillingDTO();

	public JunoChargeDTO getCharge() {
		return charge;
	}

	public void setCharge(JunoChargeDTO charge) {
		this.charge = charge;
	}

	public JunoBillingDTO getBilling() {
		return billing;
	}

	public void setBilling(JunoBillingDTO billing) {
		this.billing = billing;
	}
	
	
}
