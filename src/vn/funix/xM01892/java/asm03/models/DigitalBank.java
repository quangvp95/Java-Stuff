package vn.funix.xM01892.java.asm03.models;

import vn.funix.xM01892.java.asm02.models.Bank;
import vn.funix.xM01892.java.asm02.models.Customer;

public class DigitalBank extends Bank {
    public Customer getCustomerById(String customerId) {
        return searchCCCD(customerId);
    }

    public void addCustomer(String customerId, String name) {
        for (Customer i : getCustomers()) {
            if (i.getCustomerId().equals(customerId))
                return;
        }
        Customer customer = new DigitalCustomer();
        customer.setName(name);
        customer.setCustomerId(customerId);
        addCustomer(customer);
    }

    public boolean withdraw(String customerId, String accountNumber, double amount) {
        for (Customer i : getCustomers()) {
            if (i.getCustomerId().equals(customerId)) {
                return ((DigitalCustomer) i).withdraw(accountNumber, amount);
            }
        }
        return false;
    }

    public boolean isAccountExisted(String customerId, int accountId) {
        for (Customer i : getCustomers()) {
            if (i.getCustomerId().equals(customerId)) {
                return i.isAccountExisted(accountId);
            }
        }
        return false;
    }
}
