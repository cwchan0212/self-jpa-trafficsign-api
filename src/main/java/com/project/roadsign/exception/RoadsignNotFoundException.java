package com.project.roadsign.exception;

public class RoadsignNotFoundException extends RuntimeException{
    public RoadsignNotFoundException(Long roadsignId) {
        super("Roadsign Id not found: " + roadsignId);
    }
}

