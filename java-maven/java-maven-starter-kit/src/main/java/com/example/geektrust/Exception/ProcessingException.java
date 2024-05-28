package com.example.geektrust.Exception;

public class ProcessingException extends RuntimeException{
    public ProcessingException(){
        super();
    }
    public ProcessingException(String message){
        super(message);
    }
}
