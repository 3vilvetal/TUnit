package system;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil
{
	/**
	 * Get text with line brakes
	 * @param path
	 * @return
	 */
	public String getText(String path)
	{
		return getTextStructure(path, "\n");
	}
	
	/**
	 * Get text in one line
	 * @param path
	 * @return
	 */
	public String getString (String path)
	{
		return getTextStructure(path, "");
	}
	
	/**
	 * Gets text with pointed separators
	 * @param path
	 * @param separator
	 * @return
	 */
	private String getTextStructure (String path, String separator)
	{
		File file = new File(path);

        BufferedReader input;
        String data = "", temp; 
        
        try 
        {
             input = new BufferedReader(new FileReader(file));
             while ((temp = input.readLine())!= null)
             {
                 data += temp + separator;
             }
             input.close();
         } 
         catch (FileNotFoundException e)
         {
             e.printStackTrace();
         } 
         catch (IOException e) 
         {
             e.printStackTrace();
         }    
         return data;
	}
}
