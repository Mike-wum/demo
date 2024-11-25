package com.demo.bookstore.common.enums;

public enum OrderStatus {
    PENDING_PAYMENT(0),
    PENDING_SHIPMENT(1),
    SHIPPED(2),
    COMPLETED(3),
    CLOSED(4),
    INVALID(5),
    CANCEL(6);

    private int value;

    private OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
