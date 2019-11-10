package com.example.galgeleg.Persistent;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Loader<T>
{
	public List<T> loadSerialFile(Context context, String fileName)
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
		catch (EOFException e)
		{
			return list;
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		// Upon success
		return list;
	}
}
