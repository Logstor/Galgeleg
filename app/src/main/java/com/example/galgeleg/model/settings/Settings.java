package com.example.galgeleg.model.settings;

public class Settings
{
	private static Settings instance;
	
	//region Fields
	
	// Non-persistent
	private boolean cheatOn = false;
	
	// Persistent
	
	//endregion
	
	/**
	 * Returns the only instance of the Settings class.
	 * @return Settings object
	 */
	public static Settings getInstance()
	{
		if (instance == null)
			instance = new Settings();
		return instance;
	}
	
	/**
	 * Private constructor as it's an Singleton.
	 */
	private Settings()
	{ }
	
	/**
	 * Switches the cheat setting.
	 */
	public void switchCheats()
	{
		cheatOn = !cheatOn;
	}
	
	/**
	 * Method to determine if cheats is enabled or disabled.
	 * @return true or false depending on the cheat setting
	 */
	public boolean isCheatEnabled() { return cheatOn; }
}
