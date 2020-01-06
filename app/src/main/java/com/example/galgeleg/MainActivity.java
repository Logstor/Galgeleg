package com.example.galgeleg;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.galgeleg.Fragments.MainMenu.MainMenuFragment;

public class MainActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Start the main menu right away
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame, new MainMenuFragment())
				.commit();
	}
}
