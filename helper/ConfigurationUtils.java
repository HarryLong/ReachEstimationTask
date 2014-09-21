package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigurationUtils {
	public static final String SETTINGS_FILE = "settings.conf";

	public static Integer loadScalingFactorFromFile()
	{
		Path settingsFile = FileSystems.getDefault().getPath(SETTINGS_FILE);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(settingsFile, Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Unable to load scaling factor from file!");
			e.printStackTrace();
		}
		if(lines != null && lines.size() > 0)
			return Integer.valueOf(lines.get(0));
		else
			return null;
	}
	
	public static void writeScalingFactorToFile(int scaleFactor)
	{
	    try {
	      File file = new File(SETTINGS_FILE);
	      BufferedWriter output = new BufferedWriter(new FileWriter(file));
	      output.write(String.valueOf(scaleFactor));
	      output.close();
	    } catch ( IOException e ) {
	    	System.err.println("Unable to write to file: " + SETTINGS_FILE);
	       e.printStackTrace();
	    }
	}

}
