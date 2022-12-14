package vn.funix.xM01892.java.asm01;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Asm01 {

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

    public static class Identification {
        Province mProvince;
        Gender mGender;
        String mId;

        public final String identification;

        public Identification(String identification) {
            this.identification = identification = identification.trim();
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
                this.mGender = "N???";
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
        LIST_PROVINCES = new Province[]{new Province("H?? N???i", "001"),
                new Province("H?? Giang", "002"), new Province("Cao B???ng", "004"), new Province("B???c K???n", "006"),
                new Province("Tuy??n Quang", "008"), new Province("L??o Cai", "010"), new Province("??i???n Bi??n", "011"),
                new Province("Lai Ch??u", "012"), new Province("S??n La", "014"), new Province("Y??n B??i", "015"),
                new Province("H??a B??nh", "017"), new Province("Th??i Nguy??n", "019"), new Province("L???ng S??n", "020"),
                new Province("Qu???ng Ninh", "022"), new Province("B???c Giang", "024"), new Province("Ph?? Th???", "025"),
                new Province("V??nh Ph??c", "026"), new Province("B???c Ninh", "027"), new Province("H???i D????ng", "030"),
                new Province("H???i Ph??ng", "031"), new Province("H??ng Y??n", "033"), new Province("Th??i B??nh", "034"),
                new Province("H?? Nam", "035"), new Province("Nam ?????nh", "036"), new Province("Ninh B??nh", "037"),
                new Province("Thanh H??a", "038"), new Province("Ngh??? An", "040"), new Province("H?? T??nh", "042"),
                new Province("Qu???ng B??nh", "044"), new Province("Qu???ng Tr???", "045"), new Province("Th???a Thi??n Hu???", "046"),
                new Province("???? N???ng", "048"), new Province("Qu???ng Nam", "049"), new Province("Qu???ng Ng??i", "051"),
                new Province("B??nh ?????nh", "052"), new Province("Ph?? Y??n", "054"), new Province("Kh??nh H??a", "056"),
                new Province("Ninh Thu???n", "058"), new Province("B??nh Thu???n", "060"), new Province("Kon Tum", "062"),
                new Province("Gia Lai", "064"), new Province("?????k L???k", "066"), new Province("?????k N??ng", "067"),
                new Province("L??m ?????ng", "068"), new Province("B??nh Ph?????c", "070"), new Province("T??y Ninh", "072"),
                new Province("B??nh D????ng", "074"), new Province("?????ng Nai", "075"),
                new Province("B?? R???a - V??ng T??u", "077"), new Province("H??? Ch?? Minh", "079"),
                new Province("Long An", "080"), new Province("Ti???n Giang", "082"), new Province("B???n Tre", "083"),
                new Province("Tr?? Vinh", "084"), new Province("V??nh Long", "086"), new Province("?????ng Th??p", "087"),
                new Province("An Giang", "089"), new Province("Ki??n Giang", "091"), new Province("C???n Th??", "092"),
                new Province("H???u Giang", "093"), new Province("S??c Tr??ng", "094"), new Province("B???c Li??u", "095"),
                new Province("C?? Mau", "096")};
    }
}
