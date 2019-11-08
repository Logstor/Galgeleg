package com.example.galgeleg.Persistent;

public class Save
{
	//region Fields
	
	private static Save instance;
	
	//endregion
	
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
	
	synchronized public void saveHighscore()
	{
	
	}
}
