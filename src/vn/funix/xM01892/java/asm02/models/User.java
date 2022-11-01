package vn.funix.xM01892.java.asm02.models;

public class User {
    private String name;
    private String customerId;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if (customerId != null && customerId.length() == 12 && customerId.matches("^\\d+$")) {
            this.customerId = customerId;
        }
    }
}
