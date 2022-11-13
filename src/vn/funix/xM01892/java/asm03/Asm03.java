package vn.funix.xM01892.java.asm03;

import vn.funix.xM01892.java.asm02.models.Account;
import vn.funix.xM01892.java.asm02.models.Customer;
import vn.funix.xM01892.java.asm03.models.DigitalBank;
import vn.funix.xM01892.java.asm03.models.LoansAccount;
import vn.funix.xM01892.java.asm03.models.SavingsAccount;

import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Asm03 {
    private static final int EXIT_COMMAND_CODE = 0;
    private static final int EXIT_ERROR_CODE = -1;
    private static final Scanner scanner = new Scanner(System.in);
    private static final DigitalBank activeBank = new DigitalBank();
    private static final String CUSTOMER_ID = "001215000001";
    private static final String CUSTOMER_NAME = "FUNIX";

    static {
        activeBank.addCustomer(CUSTOMER_ID, CUSTOMER_NAME);

        Account account = new SavingsAccount();
        account.setAccountNumber("321654");
        account.setBalance(20000000);
        activeBank.addCustomer(CUSTOMER_ID, account);

        Account account1 = new LoansAccount();
        account1.setAccountNumber("321655");
        activeBank.addCustomer(CUSTOMER_ID, account1);
    }

    public static void main(String[] args) {
        onboard();
        int function;
        do {
            showFunction();
            function = inputFunction(scanner, 0, 5);
            switch (function) {
                case 0:
                    break;
                case 1: {
                    showCustomer();
                    break;
                }
                case 2: {
                    addSavingAccount();
                    break;
                }
                case 3: {
                    addLoanAccount();
                    break;
                }
                case 4: {
                    withdrawFunction();
                    break;
                }
                case 5: {
                    showHistory();
                    break;
                }
            }
        } while (function != 0);

        scanner.close();
    }

    static int inputFunction(Scanner scanner, int beginIndex, int endIndex) {
        int function;
        do {
            System.out.print("Chuc nang: ");
            try {
                function = Integer.parseInt(scanner.nextLine());
                if (function < beginIndex || function > endIndex)
                    error();
            } catch (Exception e) {
                function = -1;
                error();
            }
        } while (function < beginIndex || function > endIndex);
        return function;
    }

    static long getAmount(Scanner scanner) {
        do {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (RuntimeException ignored) {
                System.out.println("Gia tri khong hop le, vui long nhap lai");
            } catch (Exception e) {
                System.out.println("Nhap gia tri loi: " + e);
                e.printStackTrace();
                return -1;
            }
        } while (true);
    }

    static int getAccountId(Scanner scanner, String customerId, boolean needAlreadyExist) {
        do {
            System.out.println("Nhap ma STK gom 6 chu so");
            try {
                int accountId = Integer.parseInt(scanner.nextLine());
                if (100000 < accountId && accountId < 999999)
                    if (needAlreadyExist == activeBank.isAccountExisted(customerId, accountId))
                        return accountId;
            } catch (Exception ignored) {
            }
            System.out.println("STK khong hop le. Vui long nhap lai");
        } while (true);
    }

    static double getAccountBalance(Scanner scanner) {
        do {
            System.out.println("Nhap so du:");
            try {
                double balance = Double.parseDouble(scanner.nextLine());
                if (50000 < balance)
                    return balance;
                System.out.println("So du khong duoc nho hon 50000 VND. Vui long nhap lai");
            } catch (Exception e) {
                System.out.println("So du khong hop le. Vui long nhap lai");
            }
        } while (true);
    }

    static void onboard() {
        System.out.println("+------+--------------------------+------+");
        System.out.println("| NGAN HANG SO | quangnhxM01892@v1.0.0   |");
    }

    static void error() {
        System.out.println("Yeu cau khong hop le, vui long thu lai");
    }

    static void showFunction() {
        System.out.println("+------+--------------------------+------+");
        System.out.println(" 1. Thong tin khach hang                  ");
        System.out.println(" 2. Them tai khoan ATM                    ");
        System.out.println(" 3. Them tai khoan tin dung               ");
        System.out.println(" 4. Rut tien                              ");
        System.out.println(" 5. Lich su giao dich                     ");
        System.out.println(" 0. Thoat                                 ");
        System.out.println("+------+--------------------------+------+");
    }

    private static void showCustomer() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if (customer != null)
            customer.displayInformation();
    }

    private static void showHistory() {
        System.out.println("+------+--------------------------+------+");
        System.out.println("| LICH SU GIAO DICH                      |");
        System.out.println("+------+--------------------------+------+");
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if (customer != null) {
            customer.displayInformation();
            for (Account account : customer.getAccounts()) {
                account.showHistory();
            }
        }
    }

    private static void addSavingAccount() {
        Account account = new SavingsAccount();
        account.setAccountNumber(String.valueOf(getAccountId(scanner, CUSTOMER_ID, false)));
        account.setBalance(getAccountBalance(scanner));
        activeBank.addCustomer(CUSTOMER_ID, account);
        System.out.println("Them tai khoan Saving cho khach hang thanh cong");
    }

    private static void addLoanAccount() {
        Account account = new LoansAccount();
        account.setAccountNumber(String.valueOf(getAccountId(scanner, CUSTOMER_ID, false)));
        activeBank.addCustomer(CUSTOMER_ID, account);
        System.out.println("Them tai khoan Loan cho khach hang thanh cong");
    }

    private static void withdrawFunction() {
        showCustomer();
        System.out.println("Hay chon tai khoan can rut tien.");
        String accountNumber = String.valueOf(getAccountId(scanner, CUSTOMER_ID, true));

        System.out.println("Nhap so tien can rut:");
        do {
            long amount = getAmount(scanner);
            if (activeBank.withdraw(CUSTOMER_ID, accountNumber, amount)) {
                System.out.println("Rut tien thanh cong");
                return;
            }
        } while (true);
    }
}
