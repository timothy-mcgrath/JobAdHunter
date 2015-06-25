package nz.ac.ucol.configuration;

import java.io.File;

public class Configuration {
	public static String PLUGINFOLDERLOCATION;
	
	public static void findLocatAbsoultepluginpath()
	{
		PLUGINFOLDERLOCATION = new File("Plugin/").getAbsolutePath();
	}
}
