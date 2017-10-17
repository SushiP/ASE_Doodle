/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.unipi.ase.toydoodle;

import di.unipi.ase.toydoodle.exceptions.ObjectNotFoundException;
import di.unipi.ase.toydoodle.exceptions.WrongMethodCallException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Sushi
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ServiceExceptionsHandler extends ResponseEntityExceptionHandler{
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        String error = "Malformed JSON request";
        return new ResponseEntity<>(new ServiceError(HttpStatus.BAD_REQUEST, error, ex), HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        String error = "This method is not supported by this URL.";
        return new ResponseEntity<>(new ServiceError(HttpStatus.BAD_REQUEST, error, ex), HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        String error = "Argument is not valid: be sure that the object is well formed.";
        return new ResponseEntity<>(new ServiceError(HttpStatus.NOT_ACCEPTABLE, error, ex), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(value = {ObjectNotFoundException.class})
    protected ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex){
        String error = ex.getMessage();
        return new ResponseEntity<>(new ServiceError(HttpStatus.PRECONDITION_FAILED, error, ex), HttpStatus.PRECONDITION_FAILED);
    }
    
    @ExceptionHandler(value = {WrongMethodCallException.class})
    protected ResponseEntity<Object> handleWrongMethodCallException(ObjectNotFoundException ex){
        String error = ex.getMessage();
        return new ResponseEntity<>(new ServiceError(HttpStatus.BAD_REQUEST, error, ex), HttpStatus.BAD_REQUEST);
    }
}
