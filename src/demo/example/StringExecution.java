package demo.example;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StringExecution {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
//		Scanner scanner = new Scanner(new File("/home/quangnh/Work/abp/diff103.diff"));
		String line, lastLine = "";
		while ((line = scanner.nextLine()) != null) {
			if (line.isEmpty()) {
				lastLine = "";
				continue;
			}
			String myString = "";
			if (line.contains("error: resource string/")) {
				int index = line.indexOf("error: resource string/");
				int end = line.indexOf(" (aka");
				String id = line.substring(index + "error: resource string/".length(), end);
				myString = "    <message name=\"IDS_" + id.toUpperCase() + "\" formatter_data=\"android_java\">";
				System.out.println(myString);
			}
//			if (line.startsWith("IDS_")) {
//				line = line.substring(4).toLowerCase();
//			}
//			System.out.println("context.getResources().getString(R.string."+line+")");
//			System.out.println("getResources().getString(R.string."+line+")");
//			System.out.println("getString(R.string."+line+")");
//			System.out.println("R.string."+line);
//			System.out.println("@string/"+line);
//			String myString = "android:hint=\"@string/"+line+"\"\n";
//			System.out.println(myString);
//			System.out.println(line);
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			lastLine = line;
		}
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

}
