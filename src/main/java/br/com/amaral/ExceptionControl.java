package br.com.amaral;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.amaral.model.dto.ErrorObjectDTO;
import br.com.amaral.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionControl extends ResponseEntityExceptionHandler {

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	@ExceptionHandler(ExceptionProject.class)
	public ResponseEntity<Object> handleExceptionCustom(ExceptionProject ex) {

		ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();
		errorObjectDTO.setError(ex.getMessage());
		errorObjectDTO.setCode(HttpStatus.OK.toString());

		return new ResponseEntity<>(errorObjectDTO, HttpStatus.OK);
	}

	/* Catch project exceptions */
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();

		String msg = "";

		if (ex instanceof MethodArgumentNotValidException) {

			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else if (ex instanceof HttpMessageNotReadableException) {

			msg = "No data is being sent to the BODY of the request";

		} else {
			msg = ex.getMessage();
		}

		errorObjectDTO.setError(msg);
		errorObjectDTO.setCode(status.value() + " ==> " + status.getReasonPhrase());
		ex.printStackTrace();

		try {
			serviceSendEmail.sendEmailHtml("Error in the online store", ExceptionUtils.getStackTrace(ex),
					"amaral_adm@hotmail.com");

		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/* Database error capture */
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

		ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();

		String msg = "";

		if (ex instanceof DataIntegrityViolationException) {
			msg = "Database integrity error: "
					+ ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			msg = "Foreign key error: " + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof SQLException) {
			msg = "Database SQL error: " + ((SQLException) ex).getCause().getCause().getMessage();
		} else {
			msg = ex.getMessage();
		}

		errorObjectDTO.setError(msg);
		errorObjectDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		ex.printStackTrace();

		try {
			serviceSendEmail.sendEmailHtml("Error in the online store", ExceptionUtils.getStackTrace(ex),
					"amaral_adm@hotmail.com");

		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}