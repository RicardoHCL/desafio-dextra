package br.com.dextra.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.dextra.utils.Utils;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@ControllerAdvice
public class CustomMesageEntityException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> rendersExceptions(Exception ex, WebRequest request) {
		log.error("", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(Utils.getStringWithLocalDateTimeNow(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<ExceptionResponse> rendersInvalidDataException(Exception ex, WebRequest request) {
		log.error("", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(Utils.getStringWithLocalDateTimeNow(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(Utils.getStringWithLocalDateTimeNow(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFound.class)
	public final ResponseEntity<ExceptionResponse> rendersResourceNotFound(Exception ex, WebRequest request) {
		log.error("", ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(Utils.getStringWithLocalDateTimeNow(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
