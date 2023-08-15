package demo.example;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileExecution {
	private static final String[][] MAP = {{""}};

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
//		Scanner scanner = new Scanner(new File("/home/quangnh/Work/abp/diff103.diff"));
		String line = "";
		while ((line = scanner.nextLine().trim()) != null) {
			if (line.isEmpty()) {
				continue;
			}
			String src = "/home/quangnh/Work/abp/abpchromium/" + line;
			String dst = "/home/quangnh/Work/chromium_2/browser/src/" + line;

			try {
				renameFileUsingChannel(new File(src), new File(dst));
			} catch (Exception e) {
				System.out.println("Error : " + line);
				e.printStackTrace();
			}

//			int index = line.indexOf("new file mode");
//			if (index > -1) {
//				int index1 = lastLine.indexOf("a/");
//				int index2 = lastLine.indexOf("b/");
//
//				String pathFile = lastLine.substring(index1 + "a/".length(),index2);
////				String newVarName = "s" + lastLine.substring(index1 + " = \"".length()+1,index2);
////				System.out.println("public static final CachedFlag " + newVarName + " =\nnew CachedFlag("+varName+", false);");
//				System.out.println(pathFile);
//			} else {
//				lastLine = line;
//				continue;
//			}
//			lastLine = line;
		}
		scanner.close();
	}

	private static void copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		System.out.println(source.getAbsolutePath() + " | " + source.exists());
		System.out.println(dest.getAbsolutePath() + " | " + dest.exists());

		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}

	private static void renameFileUsingChannel(File source, File dest) throws IOException {
		System.out.println(source.getAbsolutePath() + " | " + source.exists());
		System.out.println(dest.getAbsolutePath() + " | " + dest.exists());

		Path sourceDir = Paths.get(source.getAbsolutePath());
		Path newdir = Paths.get(dest.getAbsolutePath());
		Files.move(sourceDir, newdir.resolve(sourceDir.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	}

}
