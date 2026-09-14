package com.avaitor.subscription_service.exception;

public class DuplicateSubscriptionRequestException extends RuntimeException {
    public DuplicateSubscriptionRequestException(String message) {
        super(message);
    }
}
