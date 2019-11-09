package com.example.galgeleg.Persistent;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.galgeleg.Model.Highscore.Highscore;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

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
	 * This will write the serializable object to the given file.
	 * If the file exists, then it will append to that file,
	 * otherwise it will create the file first.
	 * @param object implements serializable
	 * @param fileName Name of file to write to
	 * @param context Context
	 * @return True upon success
	 */
	synchronized private boolean serializeToFile(Serializable object, String fileName, Context context)
	{
		//FIXME: This needs to check whether the file already exists, and then
		// Make sure to use the correct ObjectOutputStream to ensure, that file isn't
		// corrupted.
		
		// Open Streams
		try
		(
				// Open streams
				FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)
		)
		{
			// Write the object and make sure to flush
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Success return true
		return true;
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
		// Try with resources
		try (FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND))
		{
			// Write the bytes
			outputStream.write(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Success return true
		return true;
	}
	
	/**
	 * This ObjectOutputStream should be used, when appending
	 * to a Serial file.
	 */
	public class AppendObjectOutputStream extends ObjectOutputStream
	{
		
		public AppendObjectOutputStream(OutputStream out) throws IOException
		{
			super(out);
		}
		
		/**
		 * This doesn't write a stream header to make
		 * sure the file isn't corrupted.
		 * @throws IOException Handle this
		 */
		@Override
		protected void writeStreamHeader() throws IOException
		{
			//TODO: Learn why this is necessary
			reset();
		}
	}
}
