package com.example.galgeleg.Persistent;

import android.annotation.SuppressLint;

import com.example.galgeleg.Model.Highscore.Highscore;

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
	synchronized public void saveHighscore(Highscore highscore)
	{
		//TODO: Implement this!
		@SuppressLint("DefaultLocale")
		String text = String.format("Score: %d\nName: %s\nDate: %s", highscore.getScore(),
				highscore.getName(), highscore.getDateTime().toString());
		System.out.println(String.format("Saving highscore\n%s", text));
	}
}
