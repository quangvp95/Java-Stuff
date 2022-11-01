package vn.funix.xM01892.java.asm02.models;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String id;
    private final List<Customer> customers = new ArrayList<>();

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

    public List<Customer> getCustomers() {
        return customers;
    }
}
