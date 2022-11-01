package demo.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class TextNormalization {
    public static final HashMap<String, Boolean> MAP = new HashMap<>();

    static {
        MAP.put("'", false);
        MAP.put("\"", false);
    }

    public static void main(String[] args) {
        File f = new File("TextNormalization.txt");

        BufferedReader br;
        String st = "";
        try {
            br = new BufferedReader(new FileReader(f));
            StringBuilder builder = new StringBuilder(".");
            CharacterHandler handler = CharacterHandler.getHandler();
            while ((st = br.readLine()) != null) {
                st = st.trim();
                if (st.isEmpty())
                    continue;

                for (int i = 0; i < st.length(); i++) {
                    String current = String.valueOf(st.charAt(i));
                    String prev = String.valueOf(builder.charAt(builder.length() - 1));
                    handler.handle(builder, prev, current);
                }
                if (!isDot(String.valueOf(builder.charAt(builder.length() - 1)))) {
                    builder.append(".");
                }
            }
            builder.delete(0,1);
            System.out.println(builder.toString().trim());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("ERR " + st);
            e.printStackTrace();
        }
    }

    static boolean isLetter(String s) {
        return !isSpecialCharacter(s);
    }

    public static boolean isSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.“”: ";
        for (int i = 0; i < s.length(); i++) {
            if (specialChars.contains(s.substring(i, 1))) {
                return true;
            }
        }
        return false;
    }

    public static int isBracket(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        String openBracket = "({[“";
        String closeBracket = ")}]”";
        for (int i = 0; i < s.length(); i++) {
            if (openBracket.contains(s)) {
                return 1;
            } else if (closeBracket.contains(s)) {
                return -1;
            }
        }
        return 0;
    }

    static Boolean isQuota(String s) {
        return MAP.getOrDefault(s, null);
    }

    static boolean isDot(String s) {
        return "!?.:".contains(s);
    }

    static boolean isComma(String s) {
        return "/*%^&*,".contains(s);
    }

    static boolean isSpace(String s) {
        return " ".equals(s);
    }

    public abstract static class CharacterHandler {

        public static CharacterHandler getHandler() {
            CharacterHandler consoleLogger = new SpaceHandler();
            CharacterHandler commaDotLogger = consoleLogger.setNext(new CommaDotHandler());
            CharacterHandler bracketHandler = commaDotLogger.setNext(new BracketHandler());
            CharacterHandler fileLogger = bracketHandler.setNext(new QuotaHandler());
            fileLogger.setNext(new LetterHandler());
            return consoleLogger;
        }

        protected CharacterHandler nextHandler; // The next Handler in the chain

        public CharacterHandler() {
        }

        // Set the next logger to make a list/chain of Handlers.
        public CharacterHandler setNext(CharacterHandler nextlogger) {
            this.nextHandler = nextlogger;
            return nextlogger;
        }

        public void handle(StringBuilder builder, String prev, String current) {
            if (handleCharacter(builder, prev, current)) {
                return;
            }
            if (nextHandler != null) {
                nextHandler.handle(builder, prev, current);
            } else {
                throw new RuntimeException("Unsupport handler _" + prev + "_" + current);
            }
        }

        protected abstract boolean handleCharacter(StringBuilder builder, String prev, String current);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    static class SpaceHandler extends CharacterHandler {
        @Override
        public boolean handleCharacter(StringBuilder builder, String prev, String current) {
            if (isSpace(current)) {
                if (isSpace(prev) || !isLetter(prev)) {
                    // Do nothing
                } else {
                    builder.append(current);
                }
                return true;
            }
            return false;
        }

    }

    static class CommaDotHandler extends CharacterHandler {
        @Override
        public boolean handleCharacter(StringBuilder builder, String prev, String current) {
            if (isDot(current) || isComma(current)) {
                if (isSpace(prev)) {
                    builder.replace(builder.length() - 1, builder.length(), current);
                } else {
                    builder.append(current);
                }
                return true;
            }
            return false;
        }

    }

    static class BracketHandler extends CharacterHandler {
        @Override
        public boolean handleCharacter(StringBuilder builder, String prev, String current) {
            int isBracket = isBracket(current);
            if (isBracket != 0) {
                boolean prevIsSpace = isSpace(prev);
                if (isBracket > 0) {
                    if (prevIsSpace) {
                        builder.append(current);
                    } else {
                        builder.append(" ").append(current);
                    }
                } else {
                    if (prevIsSpace) {
                        // Ex: "...{ngon ngu lap trinh }..."
                        builder.replace(builder.length() - 1, builder.length(), current);
                    } else {
                        builder.append(current);
                    }
                }
                return true;
            }
            return false;
        }

    }

    static class QuotaHandler extends CharacterHandler {
        @Override
        public boolean handleCharacter(StringBuilder builder, String prev, String current) {
            Boolean isQuota = isQuota(current);
            boolean prevIsSpace = isSpace(prev);
            if (isQuota != null) {
                if (isQuota) {
                    if (prevIsSpace) {
                        // Ex: "...'ngon ngu lap trinh '..."
                        builder.replace(builder.length() - 1, builder.length(), current);
                    } else {
                        builder.append(current);
                    }
                } else {
                    if (prevIsSpace) {
                        builder.append(current);
                    } else {
                        builder.append(" ").append(current);
                    }
                }
                MAP.put(current, !isQuota);
                return true;
            }
            return false;
        }

    }

    static class LetterHandler extends CharacterHandler {
        @Override
        public boolean handleCharacter(StringBuilder builder, String prev, String current) {
            if (isLetter(current)) {
                int isBracket = isBracket(prev);
                Boolean isQuota = isQuota(prev);
                if (isBracket != 0) {
                    if (isBracket > 0)
                        builder.append(current);
                    else
                        builder.append(" ").append(current);
                } else if (isDot(prev)) {
                    builder.append(" ").append(current.toUpperCase());
                } else if (isQuota != null) {
                    if (isQuota)
                        // Ex: "...'ngon ngu lap trinh 'cho nen..."
                        builder.append(current);
                    else
                        builder.append(" ").append(current);
                } else if (isComma(prev)) {
                    builder.append(" ").append(current);
                } else if (isLetter(prev) || isSpace(prev)) {
                    builder.append(current);
                } else {
                    throw new RuntimeException("Unsupport letter _" + prev + "_" + current);
                }
                return true;
            }
            return false;
        }
    }
}
