package com.receipt.processor.exception;


import com.receipt.processor.data.ServerError;

public class ServerErrorException extends Exception {
    public ServerError serverError;

    public ServerErrorException(String message, ServerError serverError) {
        super(message);
        this.serverError = serverError;
    }
}