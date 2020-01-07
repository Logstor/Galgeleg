package com.example.galgeleg.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galgeleg.R;

public class SettingsFragment extends Fragment implements View.OnClickListener
{
	private View view;
	
	private Switch btnSwitch;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return view = inflater.inflate(R.layout.settings_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		// Get view references
		btnSwitch = view.findViewById(R.id.switchBar);
		
		// Set OnClick listeners
		btnSwitch.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == btnSwitch)
		{
		
		}
	}
}
