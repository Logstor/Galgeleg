package com.example.galgeleg.Model.Highscore;

import java.io.Serializable;
import java.util.Date;

public class Highscore implements Serializable, Comparable<Highscore>
{
	//region Fields
	private int score;
	private String name;
	private Date date;
	//endregion
	
	private Highscore(Builder builder)
	{
		this.score = builder.score;
		this.name = builder.name;
		this.date = builder.date;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getDateTime()
	{
		return date;
	}
	
	@Override
	public int compareTo(Highscore o)
	{
		//TODO: Implement this!
		return 0;
	}
	
	/**
	 * Builder class for Highscore.
	 */
	public static class Builder
	{
		//region Fields
		private final int score;
		private final String name;
		private Date date;
		//endregion
		
		public Builder(int score, String name)
		{
			this.score = score;
			this.name = name;
		}
		
		public Builder date(Date date)
		{
			this.date = date;
			return this;
		}
		
		public Highscore build()
		{ return new Highscore(this); }
	}
}
