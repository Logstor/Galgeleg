package com.example.galgeleg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galgeleg.R;
import com.example.galgeleg.model.highscore.Highscore;
import com.example.galgeleg.model.highscore.RecyclerViewAdapter;
import com.example.galgeleg.persistent.Loader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighscoreFragment extends Fragment
{
	private TextView txt_title, txt_empty;
	private RecyclerView recycler;
	private RecyclerViewAdapter rAdapter;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.highscore_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		// Get elements
		txt_empty = view.findViewById(R.id.txt_empty);
		recycler = view.findViewById(R.id.recycler);
		
		// Setup the recycler
		setupRecycler();
	}
	
	/**
	 * Sets up the recycler view.
	 */
	private void setupRecycler()
	{
		// Load list
		List<Highscore> highscores = loadHighscores();
		
		// Check whether there's any highscores yet
		if (highscores.isEmpty())
			txt_empty.setVisibility(View.VISIBLE);
		
		else
		{
			
			// Create the Adapter
			rAdapter = new RecyclerViewAdapter(getContext(), highscores);
			
			// Set the LayoutManager to linear
			recycler.setLayoutManager(new LinearLayoutManager(getContext()));
			
			// Set the animator to default
			recycler.setItemAnimator(new DefaultItemAnimator());
			
			// Set item separator
			DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
					DividerItemDecoration.VERTICAL);
			recycler.addItemDecoration(dividerItemDecoration);
			
			// Set the adapter
			recycler.setAdapter(rAdapter);
		}
	}
	
	/**
	 * Loads all the highscores, and return them in a
	 * List.
	 * @return List<Highscore>
	 */
	private List<Highscore> loadHighscores()
	{
		// Create loader
		Loader<Highscore> loader = new Loader<>();
		
		// Load and sort the list in reverse order
		List<Highscore> list;
		try {
			list = loader.loadSerialFile(getContext(), getResources().getString(R.string.saveFile));
			Collections.sort(list, Collections.<Highscore>reverseOrder());
		}
		catch (FileNotFoundException e)
		{
			// Create empty ArrayList to avoid nullPointer
			System.out.println("No Save File exists");
			list = new ArrayList<>();
		}
		
		// Return the list
		return list;
	}
}
