package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertyFileUtil 
{
	public static String getValueForKey(String key) throws FileNotFoundException, Throwable
	{
		Properties configProperties = new Properties();
		configProperties.load(new FileInputStream(new File(".\\PropertiesFile\\Envirnoment.properties")));
		return configProperties.getProperty(key);
	}
}
