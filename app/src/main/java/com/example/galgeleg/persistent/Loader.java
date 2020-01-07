package com.example.galgeleg.persistent;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class Loader<T>
{
	/**
	 * This method loads all objects of same type from a serialized file.
	 * It returns null upon failure, and throws FileNotFoundException,
	 * when the file doesn't exist.
	 *
	 * @param context Calling Context
	 * @param fileName Name of file
	 * @return List<T/> and null upon failure
	 * @throws FileNotFoundException When file doesn't exists
	 */
	public List<T> loadSerialFile(Context context, String fileName) throws FileNotFoundException
	{
		// Buffer size in bytes
		final int BUFFER = 2048;
		
		// List
		ArrayList<T> list = new ArrayList<>();
		
		try (
				FileInputStream fileInputStream = context.openFileInput(fileName);
				BufferedInputStream bufStream = new BufferedInputStream(fileInputStream, BUFFER);
				ObjectInputStream inputStream = new ObjectInputStream(bufStream)
				)
		{
			// Read the file
			T object = (T) inputStream.readObject();
			while(object != null)
			{
				// Add to list
				list.add(object);
				
				// Update object
				object = (T) inputStream.readObject();
			}
		}
		// If file isn't found we throw
		catch (FileNotFoundException e)
		{
			throw e;
		}
		// This happens every time
		catch (EOFException e)
		{
			return list;
		}
		// If the file is corrupted
		catch (StreamCorruptedException e)
		{
			e.printStackTrace();
			System.err.println("ERROR: File corrupted!");
			return list;
		}
		// All other IO exceptions
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
			return list;
		}
		
		// Upon success
		return list;
	}
}
