package com.k2dev.hospital;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.exception.InsufficientObjectsException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import static com.k2dev.hospital.util.Constants.NOT_EXISTS;
import static com.k2dev.hospital.util.Constants.ALREADY_EXISTS;
import static com.k2dev.hospital.util.Constants.INSUFFICIENT_OBJECTS;
import static com.k2dev.hospital.util.Constants.USERNAME_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.VALIIDATION_ERROR;
import static com.k2dev.hospital.util.Constants.INVALID_INPUT;

@RestControllerAdvice
@Slf4j
public class CentralExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Handler for NotFoundException
	 */
	@ExceptionHandler({
		NotFoundException.class,
		UsernameNotFoundException.class
	})
	public ResponseEntity<Object> handleNotFoundException(Exception e, 
			WebRequest request) {
		log.error("not found exception: {}",e.getMessage(),e);
		String errMsg= e.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= NOT_EXISTS;
		
		return handleExceptionInternal(e, errMsg, 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * Same as NotFoundException but it is sub class of RuntimeException not Exception
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> handleObjectNotFoundException(RuntimeException re, 
			WebRequest request) {
		log.error("Requested objet(or s) are not available in database: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= NOT_EXISTS;
		
		return handleExceptionInternal(re, errMsg, 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * Handler for runtime-exceptions which will be thrown when data doesn't found
	 */
	@ExceptionHandler({
					NoSuchElementException.class,
					UserNotFoundException.class,
					EmptyResultDataAccessException.class,
					IllegalStateException.class
	})
	public ResponseEntity<Object> handleNotFoundException(RuntimeException re, 
			WebRequest request) {
		log.error("not found exception: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= NOT_EXISTS;
		
		return handleExceptionInternal(re, errMsg, 
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * Handler for  InsufficientObjectException
	 */
	
	@ExceptionHandler(InsufficientObjectsException.class)
	public ResponseEntity<Object> insufficientObjectsExceptionHandler(RuntimeException re, 
			WebRequest request) {
		log.error("insufficient object exception: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= INSUFFICIENT_OBJECTS;
		
		return handleExceptionInternal(re, errMsg, 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Handler for already exists exception
	 */
	@ExceptionHandler(AlreadyExistException.class)
	protected ResponseEntity<Object> alreadyExistsExceptionHandler(Exception re, 
				WebRequest request){
		log.error("already exists exception: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= ALREADY_EXISTS;
		
		return handleExceptionInternal(re, errMsg, 
				new HttpHeaders(), HttpStatus.ALREADY_REPORTED, request);
	}
	
	
	
	/**
	 * If user violates the constraint, then this handler will be fired to
	 * handle the <code>ConstraintViolationException<code>
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> constraintViolationExceptionHandler(RuntimeException re, 
			WebRequest request){
		log.error("constraints violation exception: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= VALIIDATION_ERROR;
		
		return handleExceptionInternal(re, errMsg, 
			new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Handler for IIllegalArgumentException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> illegalArgumentExceptionHandler(RuntimeException re, 
			WebRequest request){
		log.error("invalid input exception: {}",re.getMessage(), re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= INVALID_INPUT;
		
		return handleExceptionInternal(re, errMsg, 
			new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	/**
	 * Default exception handler
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> generalConflictHandler(Exception re, WebRequest request){
		log.error("default exception handler", re);
		String errMsg= re.getMessage();
		if(errMsg==null || errMsg.trim() == "")
			errMsg= "Error while doing processing";

		return handleExceptionInternal(re, errMsg, 
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
