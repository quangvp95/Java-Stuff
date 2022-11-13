package vn.funix.xM01892.java.asm03.models;

import vn.funix.xM01892.java.asm02.models.Account;
import vn.funix.xM01892.java.asm02.models.Customer;

public class DigitalCustomer extends Customer {
    boolean withdraw(String accountNumber, double amount) {
        for (Account acc : getAccounts()) {
            if (acc.getAccountNumber().equals(accountNumber))
                return acc.withdraw(amount);
        }
        return false;
    }

}
