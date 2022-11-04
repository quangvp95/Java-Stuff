package vn.funix.xM01892.java.asm02.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Bank {
    private final String id;
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
            if (i.getCustomerId().equals(customerId)) {
                i.addAccount(account);
                return;
            }
        }
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

    public Customer searchCCCD(String cccd) {
        for (Customer i : customers) {
            if (i.getCustomerId().equals(cccd))
                return i;
        }
        return null;
    }

    public List<Customer> searchCustomers(String name) {
        List<Customer> list = new ArrayList<>();
        for (Customer i : customers) {
            if (i.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                list.add(i);
        }
        return list;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
