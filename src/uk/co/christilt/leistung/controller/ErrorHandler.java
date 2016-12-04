package uk.co.christilt.leistung.controller;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller for handling errors.
 */
@ControllerAdvice
public class ErrorHandler {
	
	private static Logger LOGGER = Logger.getLogger(ErrorHandler.class);
	
	/**
	 * Logs a Spring {@link DataAccessException} and renders the error page.
	 * Note that Hibernate exceptions are translated into DataAccessExceptions
	 * by the {@link PersistenceExceptionTranslationPostProcessor}.
	 */
	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(DataAccessException ex) {
		LOGGER.error(ex);
		return "error";
	}
}
