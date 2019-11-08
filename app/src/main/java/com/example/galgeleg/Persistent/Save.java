package com.example.galgeleg.Persistent;

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
	}
}
