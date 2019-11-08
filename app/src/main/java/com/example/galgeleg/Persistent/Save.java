package com.example.galgeleg.Persistent;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.galgeleg.Model.Highscore.Highscore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save
{
	//region Fields
	
	private static Save instance;
	
	//endregion
	
	/**
	 * Private constructor for the getInstance().
	 */
	private Save()
	{}
	
	/**
	 * Gets the instance.
	 * @return Save
	 */
	public static Save getInstance()
	{
		if (instance == null)
			instance = new Save();
		return instance;
	}
	
	/**
	 * Saves a highscore.
	 * @param highscore Highscore
	 */
	synchronized public void saveHighscore(Highscore highscore, Context context)
	{
		//TODO: Implement this!
		@SuppressLint("DefaultLocale")
		String text = String.format("Score: %d\nName: %s\nDate: %s", highscore.getScore(),
				highscore.getName(), highscore.getDateTime().toString());
		System.out.println(String.format("Saving highscore\n%s", text));
		
		
	}
	
	/**
	 * Writes the given bytearray to the given filename.
	 * @param data Bytearray, byte[]
	 * @param fileName Name of file
	 * @param context Context call is coming form
	 * @return true upon success
	 */
	synchronized private boolean writeToFile(final byte[] data, String fileName, Context context)
	{
		// Open the stream
		FileOutputStream outputStream;
		try
		{ outputStream = context.openFileOutput(fileName, Context.MODE_APPEND); }
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Write the bytes
		try
		{ outputStream.write(data); outputStream.close(); }
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Success return true
		return true;
	}
}
