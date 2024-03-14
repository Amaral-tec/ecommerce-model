package br.com.amaral;

import br.com.amaral.constant.ResponseMessagesConstant;

public class ExceptionProject extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExceptionProject(String msgError) {
		super(msgError);
	}
	
	public ExceptionProject(ResponseMessagesConstant constant) {
        super(constant.toString());

    }

}
