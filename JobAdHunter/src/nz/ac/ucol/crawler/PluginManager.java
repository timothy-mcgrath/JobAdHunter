package nz.ac.ucol.crawler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
	
public class PluginManager {

	/**
	 * loads plug ins from specified folder that match the provided class 
	 * @param classToCastTo class of returned objects 
	 * @param pluginPath path to plug in .class files
	 * @return Arraylist of plug in's matching supplied class	
	 * @throws Exception invalid URL to plug in folder
	 */
	public static <T> ArrayList<T> loadPlugins(Class<T> classToCastTo,String pluginPath) throws Exception{

		File filePath = new File(pluginPath);
		File files [] = filePath.listFiles();

		ArrayList<T> plugins = new ArrayList<T>();

		// Convert File to a URL
		URI uri = filePath.toURI();
		URL url = uri.toURL();
		URL[] urls = new URL[]{url};

		// Create a new URL class loader with the directory
		@SuppressWarnings("resource")
		ClassLoader loader = new URLClassLoader(urls);


		for(File file:files){
			if(file.isFile()){

				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String firstTwoLinesOfFile = br.readLine() + br.readLine();	      // some time's leading byte code is interpreted as a new line character  
				in.close(); 
				
				// extract class name from .class file
				String className = "";
				boolean started = false; 
				for( int i = 0 ; i < firstTwoLinesOfFile.length();i++)
				{
					Character cha = firstTwoLinesOfFile.charAt(i);
					if(started == false && (cha >= 'a' && cha <= 'z') || (cha >= 'A' && cha <= 'Z'))
					{
						started = true;
					}
					if(started)
					{
						if((cha >= 'a' && cha <= 'z') || (cha >= 'A' && cha <= 'Z') || cha.equals('/'))
						{
							if(cha.equals('/'))
							{
								className += ".";
							}
							else
							{
								className += cha;
							}
						}
						else
						{
							// sometimes leading byte code comes out as letter 
							if(className.length() < 2)
							{
								className = "";
								started = false;
							}
							else								
							{
								break;
							}
						}
					}
				}
				
				try
				{
					Class cls = loader.loadClass(className);
	
					//add loaded plugin to plugin list
					plugins.add(classToCastTo.cast(cls.newInstance()));
				}
				catch (ClassCastException e)
				{
					// this class is not compatible with supplied class
				}

			}else {
				//skip folders
				continue;
			}
		}
		return plugins;
	}
}
