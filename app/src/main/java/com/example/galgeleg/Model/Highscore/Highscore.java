package com.example.galgeleg.Model.Highscore;

import com.example.galgeleg.Model.Coordinates;

import java.time.LocalDateTime;

public class Highscore
{
	//region Fields
	private int guessedLetters;
	private String name;
	private LocalDateTime dateTime;
	private Coordinates coords;
	//endregion
	
	private Highscore(Builder builder)
	{
		this.guessedLetters = builder.score;
		this.name = builder.name;
		this.coords = builder.coords;
		this.dateTime = builder.dateTime;
	}
	
	public int getGuessedLetters()
	{
		return guessedLetters;
	}
	
	public String getName()
	{
		return name;
	}
	
	public LocalDateTime getDateTime()
	{
		return dateTime;
	}
	
	/**
	 * Builder class for Highscore.
	 */
	public static class Builder
	{
		//region Fields
		private final int score;
		private final String name;
		private LocalDateTime dateTime;
		private Coordinates coords = new Coordinates(0.0, 0.0);
		//endregion
		
		public Builder(int score, String name)
		{
			this.score = score;
			this.name = name;
		}
		
		public Builder dateTime(LocalDateTime dateTime)
		{
			this.dateTime = dateTime;
			return this;
		}
		
		public Builder coords(Coordinates coords)
		{
			this.coords = coords;
			return this;
		}
		
		public Highscore build()
		{ return new Highscore(this); }
	}
}
