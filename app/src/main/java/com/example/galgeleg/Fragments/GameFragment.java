package com.example.galgeleg.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galgeleg.R;

public class GameFragment extends Fragment
{
	ImageView image;
	TextView txt_view;
	Button btn_guess;
	EditText txt_edit;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_game, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setup(view);
	}
	
	private void setup(View view)
	{
		// Find element references
		image		= view.findViewById(R.id.galge);
		txt_view 	= view.findViewById(R.id.txt_word);
		btn_guess	= view.findViewById(R.id.guess);
		txt_edit	= view.findViewById(R.id.txt_guess);
	}
}
