import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
	private static final String[][] MAP = {{""}};
				renameFileUsingChannel(new File(src), new File(dst));
	private static void renameFileUsingChannel(File source, File dest) throws IOException {
		System.out.println(source.getAbsolutePath() + " | " + source.exists());
		System.out.println(dest.getAbsolutePath() + " | " + dest.exists());

		Path sourceDir = Paths.get(source.getAbsolutePath());
		Path newdir = Paths.get(dest.getAbsolutePath());
		Files.move(sourceDir, newdir.resolve(sourceDir.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	}
