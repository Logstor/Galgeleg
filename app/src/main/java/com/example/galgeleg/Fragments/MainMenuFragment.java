package com.example.galgeleg.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.galgeleg.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener
{
	private Button btn_newGame, btn_highscores, btn_settings;
	private TextView txt_title;
	private FragmentManager fragmentManager;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_menu, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		fragmentManager = getFragmentManager();
		setup(view);
	}
	
	/**
	 * This gets all elements references, and sets onClickListeners.
	 * @param view The View from onViewCreated()
	 */
	private void setup(View view)
	{
		// Get elements
		btn_newGame 	= view.findViewById(R.id.btn_newGame);
		btn_highscores	= view.findViewById(R.id.btn_highscores);
		btn_settings	= view.findViewById(R.id.btn_settings);
		txt_title		= view.findViewById(R.id.title);
		
		// Set onClick listeners
		btn_newGame.setOnClickListener(this);
		btn_highscores.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == btn_newGame)
		{
			fragmentManager.beginTransaction()
					.replace(R.id.frame, new GameFragment())
					.addToBackStack(null)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.commit();
		}
		else if (v == btn_highscores)
		{
			Toast.makeText(getContext(), "Not Implemented", Toast.LENGTH_SHORT).show();
		}
		else if (v == btn_settings)
		{
			Toast.makeText(getContext(), "Not Implemented", Toast.LENGTH_SHORT).show();
		}
	}
}
