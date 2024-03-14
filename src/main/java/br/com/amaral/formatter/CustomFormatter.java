package br.com.amaral.formatter;

import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		String message = record.getMessage();
		Object[] parameters = record.getParameters();

		if (parameters != null && parameters.length > 0) {
			message = MessageFormat.format(message, parameters);
		}

		return "[" + record.getLevel() + "] " + record.getLoggerName() + " : " + message + "\n";
	}

}
