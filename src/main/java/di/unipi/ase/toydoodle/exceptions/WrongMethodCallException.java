/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.unipi.ase.toydoodle.exceptions;

/**
 *
 * @author Sushi
 */
public class WrongMethodCallException extends Exception{
    public WrongMethodCallException(String methodCalled, String methodToCall){
        super("You have called method " + methodCalled + "; you should call method " + methodToCall + " for this operation");
    }
    
    public WrongMethodCallException(String methodCalled, String methodToCall, String operation){
        super("You have called method " + methodCalled + "; you should call method " + methodToCall + operation);
    }
}
