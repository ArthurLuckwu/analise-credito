package com.cdt.credito.errorHandling;

import java.io.File;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialExpiredException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdt.credito.exceptions.DuplicatedRecordException;
import com.cdt.credito.exceptions.InvalidCaptchaException;
import com.cdt.credito.exceptions.InvalidEventException;
import com.cdt.credito.exceptions.ReadOnlyRecordException;
import com.cdt.credito.exceptions.RecordNotFoundException;
import com.cdt.credito.exceptions.WeakPasswordException;

@ControllerAdvice(annotations = RestController.class)
public class RestErrorHandler {
	private static final String INTERNAL_UNEXPECTED_ERROR_MSG = "Ocorreu um erro inesperado. Favor contactar o suporte.";
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody ResponseEntity<Void> processValidationError(MethodArgumentNotValidException ex) {
		ex.printStackTrace();
		List<String> fields = new ArrayList<>();
		List<String> fieldsErrorMessages = new ArrayList<>();
		List<String> otherErrorMessages = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			if (error instanceof FieldError) {
				FieldError fieldError = (FieldError) error;
				fields.add(fieldError.getField());
				fieldsErrorMessages.add(fieldError.getDefaultMessage());
			} else {
				otherErrorMessages.add(error.getDefaultMessage());
			}
		}

		fieldsErrorMessages.addAll(otherErrorMessages);
		return ResponseEntity.badRequest()
				.header(ErrorCode.errorCodeKey(), ErrorCode.ARGUMENTS_NOT_VALID.getValue().toString())
				.header(ErrorCode.errorFieldKey(), StringUtils.join(fields, ';'))
				.header(ErrorCode.errorMessageKey(), StringUtils.join(fieldsErrorMessages, ';')).build();
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public @ResponseBody ResponseEntity<Void> processValidationError(ConstraintViolationException ex) {
		ex.printStackTrace();
		List<String> fields = new ArrayList<>();
		List<String> fieldsErrorMessages = new ArrayList<>();
		for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
			fields.add(error.getPropertyPath().toString());
			fieldsErrorMessages.add(error.getMessage());
		}

		return ResponseEntity.badRequest()
				.header(ErrorCode.errorCodeKey(), ErrorCode.ARGUMENTS_NOT_VALID.getValue().toString())
				.header(ErrorCode.errorFieldKey(), StringUtils.join(fields, ';'))
				.header(ErrorCode.errorMessageKey(), StringUtils.join(fieldsErrorMessages, ';')).build();
	}

	@ExceptionHandler(DuplicatedRecordException.class)
	public @ResponseBody ResponseEntity<Void> processValidationError(DuplicatedRecordException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest()
				.header(ErrorCode.errorCodeKey(), ErrorCode.DUPLICATE_RECORD.getValue().toString())
				.header(ErrorCode.errorFieldKey(), ex.getField())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public @ResponseBody ResponseEntity<Void> processValidationError(RecordNotFoundException ex) {
		ex.printStackTrace();
		return ResponseEntity.notFound()
				.header(ErrorCode.errorCodeKey(), ErrorCode.RECORD_NOT_FOUND.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(FileSystemException.class)
	public @ResponseBody ResponseEntity<Void> processValidationError(FileSystemException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest()
				.header(ErrorCode.errorCodeKey(), ErrorCode.FILE_SYSTEM_ERROR.getValue().toString())
				.header(ErrorCode.errorFieldKey(), new File(ex.getFile()).getName())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(BadCredentialsException ex) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.header(ErrorCode.errorCodeKey(), ErrorCode.BAD_CREDENTIALS.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(CredentialExpiredException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(CredentialExpiredException ex) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.header(ErrorCode.errorCodeKey(), ErrorCode.PASSWORD_EXPIRED.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(WeakPasswordException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(WeakPasswordException ex) {
		ex.printStackTrace();
		return ResponseEntity.ok().header(ErrorCode.errorCodeKey(), ErrorCode.WEAK_PASSWORD.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(ReadOnlyRecordException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(ReadOnlyRecordException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().header(ErrorCode.errorCodeKey(), ErrorCode.READONLY_RECORD.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}

	@ExceptionHandler(InvalidCaptchaException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(InvalidCaptchaException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().header(ErrorCode.errorCodeKey(), ErrorCode.INVALID_CAPTCHA.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(IllegalArgumentException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().header(ErrorCode.errorCodeKey(), ErrorCode.ILLEGAL_ARGUMENT.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}
	
	@ExceptionHandler(InvalidEventException.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(InvalidEventException ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().header(ErrorCode.errorCodeKey(), ErrorCode.INVALID_EVENT.getValue().toString())
				.header(ErrorCode.errorMessageKey(), ex.getMessage()).build();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Void> processValidationError(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().header(ErrorCode.errorCodeKey(), ErrorCode.INTERNAL_UNEXPECTED_ERROR.getValue().toString())
				.header(ErrorCode.errorMessageKey(), INTERNAL_UNEXPECTED_ERROR_MSG).build();
	}
}
