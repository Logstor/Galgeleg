package com.example.galgeleg.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

import com.example.galgeleg.Logic.Galgelogik;
import com.example.galgeleg.R;

public class MainMenuFragment extends Fragment implements View.OnClickListener
{
	private static boolean wordsLoaded = false;
	
	private Button btn_newGame, btn_highscores, btn_settings;
	private ProgressDialog progressDialog;
	private TextView txt_title;
	private FragmentManager fragmentManager;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_menu, container, false);
	}
	
	@SuppressLint("StaticFieldLeak")
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		fragmentManager = getFragmentManager();
		
		setup(view);
		
		if (!wordsLoaded)
			getWordsOnline();
		
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
		
		// Create progress dialog
		progressDialog = new ProgressDialog(getContext());
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == btn_newGame)
		{
			if (wordsLoaded)
				startGame();
			else
			{
				progressDialog.setTitle("Henter ord fra DR");
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setIcon(R.drawable.downloadvec);
				progressDialog.setCancelable(false);
				progressDialog.show();
			}
		}
		else if (v == btn_highscores)
		{
			startHighscores();
		}
		else if (v == btn_settings)
		{
			Toast.makeText(getContext(), "Not Implemented", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * This method changes the fragment to
	 * GameFragment.
	 */
	private void startGame()
	{
		fragmentManager.beginTransaction()
				.replace(R.id.frame, new GameFragment())
				.addToBackStack(null)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commit();
	}
	
	/**
	 * This method changes the fragment to
	 * HighscoreFragment.
	 */
	private void startHighscores()
	{
		fragmentManager.beginTransaction()
				.replace(R.id.frame, new HighscoreFragment())
				.addToBackStack(null)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commit();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void getWordsOnline()
	{
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected void onPreExecute()
			{
				//TODO: Add check for internet connection is available
				// Otherwise use hardcoded words
				
				//TODO: Implement caching
			}
			
			@Override
			protected Void doInBackground(Void... voids)
			{
				// Cancel if for some reason it's cancelled
				if (isCancelled())
					return null;
				
				// Load from DR
				try {
					Galgelogik.getInstance().hentOrdFraDr();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("\nERROR: Couldn't load words from web\n");
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void aVoid)
			{
				// Set load boolean true
				wordsLoaded = true;
				
				// Check if it needs to change fragment
				if (progressDialog.isShowing())
				{
					// Remove dialog
					progressDialog.dismiss();
					
					// Change fragment
					startGame();
				}
			}
			
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				//TODO: Implement me!
			}
			
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
}
