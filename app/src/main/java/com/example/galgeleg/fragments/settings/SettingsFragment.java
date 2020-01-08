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
import com.example.galgeleg.model.settings.Settings;

public class SettingsFragment extends Fragment implements View.OnClickListener
{
	// Fields
	private Settings settings;
	private Switch btnSwitch;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.settings_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		// Get the settings instance
		settings = Settings.getInstance();
		
		// Get view references
		btnSwitch = view.findViewById(R.id.switchBar);
		
		// Set OnClick listeners
		btnSwitch.setOnClickListener(this);
		
		setBtnSwitch();
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == btnSwitch)
		{ settings.switchCheats(); }
	}
	
	/**
	 * Takes care of setting the slider to the correct state
	 * depending on what the setting is.
	 */
	private void setBtnSwitch()
	{
		if (settings.isCheatEnabled()) btnSwitch.setChecked(true);
	}
}
