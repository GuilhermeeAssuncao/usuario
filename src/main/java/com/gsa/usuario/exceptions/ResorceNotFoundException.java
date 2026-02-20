package com.gsa.usuario.exceptions;

public class ResorceNotFoundException extends RuntimeException{

    public ResorceNotFoundException(String mensage){
        super(mensage);
    }
    public ResorceNotFoundException(String mensage,Throwable throwable){
        super(mensage,throwable);
    }



}
