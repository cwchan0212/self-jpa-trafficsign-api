package com.project.trafficsign.exception;

public class TrafficsignNotFoundException extends RuntimeException{
    public TrafficsignNotFoundException(Long trafficsignId) {
        super("Trafficsign Id not found: " + trafficsignId);
    }
}

