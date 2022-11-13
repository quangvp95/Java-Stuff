package vn.funix.xM01892.java.asm03.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final String id;
    private final String accountNumber;
    private final double amount;
    private final String time;
    private final boolean status;

    public Transaction(String accountNumber, double amount, boolean status) {
        id = String.valueOf(UUID.randomUUID());
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = getDateTime();
        this.status = status;
    }

    @Override
    public String toString() {
        return accountNumber + " |   " + String.format("%14.0f", amount) + "đ"
                + " |     " + (status ? "SUCCESS" : " FAILED") + " |   " + time;
    }

    public static String getDateTime() {
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
