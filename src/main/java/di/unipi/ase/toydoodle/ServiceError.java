/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.unipi.ase.toydoodle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sushi
 */
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
public class ServiceError {
    @JsonProperty("statusMessage")
    private HttpStatus status;
    @JsonProperty("status")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("debugMessage")
    private String debugMessage;
    @JsonProperty("timestamp")
    private String timestamp;
    
    ServiceError(HttpStatus status, String message, Throwable ex){
        this.timestamp = (new Date(System.currentTimeMillis())).toString();
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
    
    /**
     * Return the http status of the error.
     * @return the http status of the error.
     */
    public HttpStatus getStatus(){
        return this.status;
    }
    
    public String toString(){
        return this.message + " - " + this.debugMessage + " - " + this.timestamp;
    }
}
