package com.example.galgeleg.fragments.mainMenu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.galgeleg.R;
import com.example.galgeleg.fragments.GameFragment;
import com.example.galgeleg.fragments.HighscoreFragment;
import com.example.galgeleg.fragments.faceOff.LoaderFragment;
import com.example.galgeleg.fragments.settings.SettingsFragment;
import com.example.galgeleg.logic.Galgelogik;

public class MainMenuFragment extends Fragment implements View.OnClickListener
{
	private static boolean wordsLoaded = false;
	
	private Button btn_newGame, btn_faceOff, btn_highscores, btn_settings;
	private ProgressDialog progressDialog;
	private TextView txt_title;
	private FragmentManager fragmentManager;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.menu_fragment, container, false);
	}
	
	@SuppressLint("StaticFieldLeak")
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
		btn_faceOff		= view.findViewById(R.id.btn_faceOff);
		btn_highscores	= view.findViewById(R.id.btn_highscores);
		btn_settings	= view.findViewById(R.id.btn_settings);
		txt_title		= view.findViewById(R.id.title);
		
		// Set onClick listeners
		btn_newGame.setOnClickListener(this);
		btn_faceOff.setOnClickListener(this);
		btn_highscores.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		
		// Create progress dialog
		progressDialog = new ProgressDialog(getContext());
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == btn_newGame)
			startGame(false);
		else if (v == btn_faceOff)
			startFaceOff();
		else if (v == btn_highscores)
			startHighscores();
		else if (v == btn_settings)
			startSettings();
	}
	
	/**
	 * Finds out whether there's a network connection or not.
	 * @return true or false
	 */
	private boolean isNetworkAvailable() {
		getContext();
		
		try {
			ConnectivityManager connectivityManager =
					(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			
			return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
		catch (NullPointerException e)
		{
			System.err.println("WARNING: No network connection!");
			return false;
		}
		
	}
	
	/**
	 * This method changes the fragment to the
	 * GameFragment.
	 * @param faceOff true if it's faceOff mode
	 */
	@SuppressLint("StaticFieldLeak")
	private void startGame(final boolean faceOff)
	{
		// Start progress dialog
		progressDialog.setTitle("Henter ord fra DR");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIcon(R.drawable.downloadvec);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		// Start loading asynchronously
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected void onPreExecute()
			{
				// Checking network connectivity
				if (!isNetworkAvailable())
					cancel(false);
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
				// Remove dialog first
				if (progressDialog.isShowing())
					progressDialog.dismiss();
				
				// Change fragment depending on which button was clicked
				if (faceOff)
					fragmentManager.beginTransaction()
					.replace(R.id.frame, new LoaderFragment(Galgelogik.getInstance().muligeOrd))
					.addToBackStack(null)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.commit();
				else {
					fragmentManager.beginTransaction()
							.replace(R.id.frame, new GameFragment())
							.addToBackStack(null)
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.commit();
				}
			}
			
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}
			
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
	}
	
	private void startFaceOff()
	{
		startGame(true);
	}
	
	/**
	 * This method changes the fragment to the
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
	
	/**
	 * This method changes the fragment to the
	 * SettingsFragment.
	 */
	private void startSettings()
	{
		fragmentManager.beginTransaction()
				.replace(R.id.frame, new SettingsFragment())
				.addToBackStack(null)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commit();
	}
}
