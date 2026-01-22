package ru.artteam.spendly_app.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {super(message);}
}
