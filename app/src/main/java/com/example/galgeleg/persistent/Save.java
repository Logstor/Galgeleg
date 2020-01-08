package com.example.galgeleg.persistent;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.galgeleg.R;
import com.example.galgeleg.model.highscore.Highscore;

import java.io.BufferedOutputStream;
import java.io.File;
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
	 * @return True upon success
	 */
	synchronized public boolean saveHighscore(Highscore highscore, Context context)
	{
		// Debug information
		@SuppressLint("DefaultLocale")
		String text = String.format("Score: %d\nName: %s\nDate: %s", highscore.getScore(),
				highscore.getName(), highscore.getDateTime().toString());
		System.out.println(String.format("Saving highscore\n%s", text));
		
		// Get the filename
		String fileName = context.getResources().getString(R.string.saveFile);
		
		// Save it
		return serializeToFile(highscore, fileName, context);
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
		// Buffer size in bytes
		final int BUFFER = 512;
		
		// This needs to check whether the file already exists, and then
		// Make sure to use the correct ObjectOutputStream to ensure, that file isn't
		// corrupted.
		if ( new File(context.getFilesDir(), fileName).exists() )
		{
			// Open Streams
			try (
					FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER);
					AppendObjectOutputStream objectOutputStream = new AppendObjectOutputStream(bufferedOutputStream)
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
		}
		
		
		else
		{
			// Open Streams
			try
					(
							FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
							ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)
					) {
				// Write the object and make sure to flush
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
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
		try (
				FileOutputStream fileStream = context.openFileOutput(fileName, Context.MODE_APPEND);
				BufferedOutputStream outputStream = new BufferedOutputStream(fileStream) )
		{
			// Write the bytes
			outputStream.write(data);
			outputStream.flush();
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
	private class AppendObjectOutputStream extends ObjectOutputStream
	{
		
		private AppendObjectOutputStream(OutputStream out) throws IOException
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
