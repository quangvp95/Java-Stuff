package vn.funix.xM01892.java.asm02.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String id;
    private final List<Customer> customers;

    public Bank() {
        id = String.valueOf(UUID.randomUUID());
        customers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addCustomer(Customer customer) {
        if (customer == null) return;
        for (Customer i : customers) {
            if (i.getCustomerId().equals(customer.getCustomerId()))
                return;
        }
        customers.add(customer);
    }

    public void addCustomer(String customerId, Account account) {
        for (Customer i : customers) {
            if (i.getCustomerId().equals(customerId))
                return;
        }
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.addAccount(account);
        customers.add(customer);
    }

    public boolean isCustomerExisted(String customerId) {
        for (Customer i : customers) {
            if (i.getCustomerId().equals(customerId))
                return true;
        }
        return false;
    }

    public void showCustomers() {
        for (Customer i : customers) {
            i.displayInformation();
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
