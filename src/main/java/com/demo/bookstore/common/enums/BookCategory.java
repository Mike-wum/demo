package com.demo.bookstore.common.enums;

public enum BookCategory {
    LITERATURE(0),
    FICTION(1),
    HISTORY(2),
    CHILDREN(3),
    POETRY(4),
    SCIENCE(5);

    private int value;

    private BookCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
