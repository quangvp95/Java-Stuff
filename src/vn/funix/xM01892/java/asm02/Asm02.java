package vn.funix.xM01892.java.asm02;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Asm02 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        onboard();
        int function = inputFunction(input, 0, 1);
        if (function == 0) {
            input.close();
            return;
        }
        // function == 1
        if (!verify(input)) {
            input.close();
            return;
        }
        inputIdentification();
        Identification identification = getCCCD(input);
        if (identification == null) {
            input.close();
            return;
        }
        // identification valid
        do {
            showFunction();
            function = inputFunction(input, 0, 3);
            switch (function) {
                case 0:
                    break;
                case 1: {
                    identification.showProvince();
                    break;
                }
                case 2: {
                    identification.showAge();
                    break;
                }
                case 3: {
                    identification.showId();
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
        String code;
        Identification identifyObject = null;
        do {
            try {
                code = scanner.nextLine();
                if ("No".equals(code))
                    return null;
                identifyObject = new Identification(code);
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

    static void onboard() {
        System.out.println("+------+--------------------------+------+");
        System.out.println("| NGAN HANG SO | quangnhxM01892@v1.0.0   |");
        System.out.println("+------+--------------------------+------+");
        System.out.println("| 1. Nhap CCCD                           |");
        System.out.println("| 0. Thoat                               |");
        System.out.println("+------+--------------------------+------+");
    }

    private static char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

    static boolean verify(Scanner scanner) {
        String code = String.valueOf(generatePassword(6));

        System.out.println("Nhap ma xac thuc: " + code);
        String verifyCode;
        do {
            try {
                verifyCode = scanner.nextLine();
                if ("No".equals(verifyCode)) {
                    return false;
                } else if (!Objects.equals(verifyCode, code)) {
                    System.out.println("Ma xac thuc khong dung. Vui long thu lai.");
                }
            } catch (Exception e) {
                System.out.println("Xac thuc loi: " + e);
                e.printStackTrace();
                return false;
            }
        } while (!Objects.equals(verifyCode, code));
        return true;
    }

    static void inputIdentification() {
        System.out.print("Vui long nhap so CCCD: ");
    }

    static void error() {
        System.out.println("Yeu cau khong hop le, vui long thu lai");
    }

    static void errorIdentificationMess() {
        System.out.println("So CCCD khong hop le.");
        System.out.print("Vui long nhap lai hoac 'No' de thoat: ");
    }

    static void showFunction() {
        System.out.println("    | 1. Kiem tra noi sinh");
        System.out.println("    | 2. Kiem tra tuoi, gioi tinh");
        System.out.println("    | 3. kiem tra so ngau nhien");
        System.out.println("    | 0. Thoat");
    }

    static class Identification {
        Province mProvince;
        Gender mGender;
        String mId;

        public Identification(String identification) {
            identification = identification.trim();
            if (identification.length() != 12 || !identification.matches("^\\d+$")) {
                throw new RuntimeException("Identification is not 12 digits");
            }


            String province = identification.substring(0, 3);
            String gender = identification.substring(3, 4);
            String birth = identification.substring(4, 6);
            mId = identification.substring(6, 12);

            for (Province i : LIST_PROVINCES)
                if (i.mId.equals(province)) {
                    mProvince = i;
                }
            if (mProvince == null) {
                throw new RuntimeException("Province Id invalid");
            }

            try {
                int genderId = Integer.parseInt(gender);
                int birthday = Integer.parseInt(birth);
                mGender = new Gender(genderId, birthday);
            } catch (NumberFormatException e) {
                throw new RuntimeException("gender and birth invalid: " + gender + " - " + birth);
            }
        }

        void showProvince() {
            System.out.println("Noi sinh: " + mProvince.mName);
        }

        void showAge() {
            System.out.println("Gioi tinh: " + mGender);
        }

        void showId() {
            System.out.println("So ngau nhien: " + mId);
        }

    }

    static class Province {
        String mName;
        String mId;

        public Province(String mName, String mId) {
            this.mName = mName;
            this.mId = mId;
        }
    }

    static class Gender {
        int mId;
        String mGender;
        int mBirth;

        public Gender(int id, int birth) {
            this.mId = id;
            if (id % 2 == 0) {
                this.mGender = "Nam";
            } else {
                this.mGender = "Nữ";
            }
            this.mBirth = birth + 1900 + (id / 2) * 100;
        }

        @Override
        public String toString() {
            return mGender + " | " + mBirth;
        }
    }

    public static final Province[] LIST_PROVINCES;

    static {
        LIST_PROVINCES = new Province[]{new Province("Hà Nội", "001"),
                new Province("Hà Giang", "002"), new Province("Cao Bằng", "004"), new Province("Bắc Kạn", "006"),
                new Province("Tuyên Quang", "008"), new Province("Lào Cai", "010"), new Province("Điện Biên", "011"),
                new Province("Lai Châu", "012"), new Province("Sơn La", "014"), new Province("Yên Bái", "015"),
                new Province("Hòa Bình", "017"), new Province("Thái Nguyên", "019"), new Province("Lạng Sơn", "020"),
                new Province("Quảng Ninh", "022"), new Province("Bắc Giang", "024"), new Province("Phú Thọ", "025"),
                new Province("Vĩnh Phúc", "026"), new Province("Bắc Ninh", "027"), new Province("Hải Dương", "030"),
                new Province("Hải Phòng", "031"), new Province("Hưng Yên", "033"), new Province("Thái Bình", "034"),
                new Province("Hà Nam", "035"), new Province("Nam Định", "036"), new Province("Ninh Bình", "037"),
                new Province("Thanh Hóa", "038"), new Province("Nghệ An", "040"), new Province("Hà Tĩnh", "042"),
                new Province("Quảng Bình", "044"), new Province("Quảng Trị", "045"), new Province("Thừa Thiên Huế", "046"),
                new Province("Đà Nẵng", "048"), new Province("Quảng Nam", "049"), new Province("Quảng Ngãi", "051"),
                new Province("Bình Định", "052"), new Province("Phú Yên", "054"), new Province("Khánh Hòa", "056"),
                new Province("Ninh Thuận", "058"), new Province("Bình Thuận", "060"), new Province("Kon Tum", "062"),
                new Province("Gia Lai", "064"), new Province("Đắk Lắk", "066"), new Province("Đắk Nông", "067"),
                new Province("Lâm Đồng", "068"), new Province("Bình Phước", "070"), new Province("Tây Ninh", "072"),
                new Province("Bình Dương", "074"), new Province("Đồng Nai", "075"),
                new Province("Bà Rịa - Vũng Tàu", "077"), new Province("Hồ Chí Minh", "079"),
                new Province("Long An", "080"), new Province("Tiền Giang", "082"), new Province("Bến Tre", "083"),
                new Province("Trà Vinh", "084"), new Province("Vĩnh Long", "086"), new Province("Đồng Tháp", "087"),
                new Province("An Giang", "089"), new Province("Kiên Giang", "091"), new Province("Cần Thơ", "092"),
                new Province("Hậu Giang", "093"), new Province("Sóc Trăng", "094"), new Province("Bạc Liêu", "095"),
                new Province("Cà Mau", "096")};
    }
}
