package demo.example;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StringExecution {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
//		Scanner scanner = new Scanner(new File("/home/quangnh/Work/abp/diff103.diff"));
        String line, lastLine = "";
        StringBuilder builder = new StringBuilder();
        String builder2 = "";
        while ((line = scanner.nextLine()) != null) {
            if ("exit".equals(line))
                break;
            if (!line.contains("final CachedFlag s")) {
                lastLine = "";
                continue;
            }

            int index = line.indexOf("final CachedFlag s") + "final CachedFlag ".length();
            System.out.println(line.substring(index, line.indexOf(" =")) + ",");
//            builder.append("    },\n" + "    {\n" + "      \"name\": \"").append(arr[0]).append("\",\n").append("      \"meaning\": \"\",\n").append("      \"condition\": \"\",\n").append("      \"description\": \"\",\n").append("      \"translation_vi\": \"").append(arr[1]).append("\",\n").append("      \"translation_en\": \"").append(arr[2]).append("\"\n");
//			System.out.println("context.getResources().getString(R.string."+line+")");
//			System.out.println("getResources().getString(R.string."+line+")");
//			System.out.println("getString(R.string."+line+")");
//			System.out.println("R.string."+line);
//			System.out.println("@string/"+line);
//			String myString = "android:hint=\"@string/"+line+"\"\n";
//			System.out.println(line);
//            System.out.println(myString);
//            StringSelection stringSelection = new StringSelection(myString);
//            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            clipboard.setContents(stringSelection, null);
            lastLine = line;
        }
        String myString = builder.append(builder2).toString();
        System.out.println(myString);
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        scanner.close();
    }

    private static String convert(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                String next = str.charAt(++i) + "";
                result.append(next.toUpperCase());
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    private static String uppercaseFirstLetter(String var) {
        String[] arr = var.split(Pattern.quote("_"));
        StringBuilder functionName = new StringBuilder();
        for (String i : arr)
            if (i.length() > 0) {
                functionName.append(i.substring(0, 1).toUpperCase()).append(i.substring(1).toLowerCase());
            }
        return String.valueOf(functionName);
    }

}
