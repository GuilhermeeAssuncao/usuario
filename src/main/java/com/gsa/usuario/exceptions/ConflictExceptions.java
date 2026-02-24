package com.gsa.usuario.exceptions;

public class ConflictExceptions extends RuntimeException{

    public ConflictExceptions(String mensagem){
        super(mensagem);
    }

    public ConflictExceptions(String mensagem, Throwable thowable){
        super(mensagem);
    }
}
