package vn.funix.xM01892.java.asm02;

import vn.funix.xM01892.java.asm01.Asm01.Identification;
import vn.funix.xM01892.java.asm02.models.Account;
import vn.funix.xM01892.java.asm02.models.Bank;
import vn.funix.xM01892.java.asm02.models.Customer;

import java.util.List;
import java.util.Scanner;

public class Asm02 {

    private static final Bank bank = new Bank();

    static {
        Customer customer1 = new Customer();
        customer1.setName("Huy Quang");
        customer1.setCustomerId("001215000001");
        bank.addCustomer(customer1);

        Account account1 = new Account();
        account1.setAccountNumber("123456");
        account1.setBalance(1000000);
        bank.addCustomer(customer1.getCustomerId(), account1);

        Account account3 = new Account();
        account3.setAccountNumber("111111");
        account3.setBalance(100000000);
        bank.addCustomer(customer1.getCustomerId(), account3);

        Account account4 = new Account();
        account4.setAccountNumber("111112");
        account4.setBalance(1000000);
        bank.addCustomer(customer1.getCustomerId(), account4);

        Customer customer2 = new Customer();
        customer2.setName("Hong Quang");
        customer2.setCustomerId("079025000002");
        bank.addCustomer(customer2);

        Account account2 = new Account();
        account2.setAccountNumber("654123");
        account2.setBalance(1000000);
        bank.addCustomer("037153000003", account2);

        Customer customer3 = new Customer();
        customer3.setName("Quang Minh");
        customer3.setCustomerId("001215000002");
        bank.addCustomer(customer3);

        Account account5 = new Account();
        account5.setAccountNumber("123455");
        account5.setBalance(1000000);
        bank.addCustomer(customer3.getCustomerId(), account5);

        Account account6 = new Account();
        account6.setAccountNumber("111114");
        account6.setBalance(100000000);
        bank.addCustomer(customer3.getCustomerId(), account6);

        Customer customer4 = new Customer();
        customer4.setName("Hong Minh");
        customer4.setCustomerId("001215000003");
        bank.addCustomer(customer4);

        Account account7 = new Account();
        account7.setAccountNumber("123457");
        account7.setBalance(1000000);
        bank.addCustomer(customer4.getCustomerId(), account7);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        onboard();
        int function;
        do {
            showFunction();
            function = inputFunction(input, 0, 5);
            switch (function) {
                case 0:
                    break;
                case 1: {
                    String name = getCustomerName(input);
                    Identification identification = getCCCD(input);
                    if (identification == null) {
                        continue;
                    }
                    String cccd = identification.identification;
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setCustomerId(cccd);
                    bank.addCustomer(customer);
                    System.out.println("Them khach hang thanh cong");
                    break;
                }
                case 2: {
                    Identification identification = getCCCDKhachHang(input);
                    if (identification == null) {
                        continue;
                    }
                    Account account = new Account();
                    account.setAccountNumber(String.valueOf(getAccountId(input)));
                    account.setBalance(getAccountBalance(input));
                    bank.addCustomer(identification.identification, account);
                    System.out.println("Them tai khoan cho khach hang thanh cong");
                    break;
                }
                case 3: {
                    bank.showCustomers();
                    break;
                }
                case 4: {
                    Identification identification = getCCCD(input);
                    if (identification == null) {
                        continue;
                    }
                    Customer customer = bank.searchCCCD(identification.identification);
                    if (customer != null)
                        customer.displayInformation();
                    else
                        System.out.println("Khong tim thay tai khoan");
                    break;
                }
                case 5: {
                    String name = getCustomerName(input);
                    List<Customer> list = bank.searchCustomers(name);
                    if (list.size() > 0)
                        for (Customer i : list) {
                            i.displayInformation();
                        }
                    else
                        System.out.println("Khong tim thay tai khoan");
                    break;
                }
            }
        } while (function != 0);

        input.close();
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

    static Identification getCCCD(Scanner scanner) {
        Identification identifyObject = null;
        do {
            System.out.println("Nhap so CCCD:");
            try {
                String cccd = scanner.nextLine();
                if ("No".equals(cccd))
                    return null;
                identifyObject = new Identification(cccd);
            } catch (RuntimeException ignored) {
                errorIdentificationMess();
            } catch (Exception e) {
                System.out.println("Nhap CCCD loi: " + e);
                e.printStackTrace();
                return null;
            }
        } while (identifyObject == null);

        return identifyObject;
    }

    static Identification getCCCDKhachHang(Scanner scanner) {
        do {
            Identification identification = getCCCD(scanner);
            if (identification == null) {
                return null;
            }
            if (bank.isCustomerExisted(identification.identification))
                return identification;
            System.out.println("CCCD khong ton tai trong he thong. Vui long nhap lai.");
        } while (true);
    }

    static int getAccountId(Scanner scanner) {
        do {
            System.out.println("Nhap ma STK gom 6 chu so");
            try {
                int function = Integer.parseInt(scanner.nextLine());
                if (100000 < function && function < 999999)
                    return function;
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

    static String getCustomerName(Scanner scanner) {
        System.out.println("Nhap ten khach hang:");
        String name = "";
        do {
            try {
                name = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Nhap ten: " + e);
                e.printStackTrace();
            }
        } while (name.isEmpty());
        return name;
    }

    static void error() {
        System.out.println("Yeu cau khong hop le, vui long thu lai");
    }

    static void errorIdentificationMess() {
        System.out.println("So CCCD khong hop le. Vui long nhap lai.");
    }

    static void showFunction() {
        System.out.println("+------+--------------------------+------+");
        System.out.println(" 1. Them khach hang                       ");
        System.out.println(" 2. Them tai khoan cho khach hang         ");
        System.out.println(" 3. Hien thi danh sach khach hang         ");
        System.out.println(" 4. Tim theo CCCD                         ");
        System.out.println(" 5. Tim theo ten khach hang              ");
        System.out.println(" 0. Thoat                                 ");
        System.out.println("+------+--------------------------+------+");
    }
}
