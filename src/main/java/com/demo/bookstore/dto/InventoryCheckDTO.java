package com.demo.bookstore.dto;

import java.io.Serializable;

public class InventoryCheckDTO implements Serializable {
    private boolean available;

    public InventoryCheckDTO() {}

    public InventoryCheckDTO(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
