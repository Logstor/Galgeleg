package com.example.galgeleg.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galgeleg.R;
import com.example.galgeleg.fragments.mainMenu.MainMenuFragment;
import com.example.galgeleg.logic.Galgelogik;
import com.example.galgeleg.model.highscore.Highscore;
import com.example.galgeleg.model.settings.Settings;
import com.example.galgeleg.persistent.Save;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GameFragment extends Fragment implements View.OnClickListener
{
	private View view;
	private ImageView image, img_correct, img_wrong;
	private TextView txt_view, txt_score, txt_cheat;
	private List<Button> buttons;
	private int score = 0;
	
	private MediaPlayer mediaWin;
	private MediaPlayer mediaLoss;
	
	private Galgelogik logic 		= Galgelogik.getInstance();
	private Settings settings 		= Settings.getInstance();
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return view = inflater.inflate(R.layout.game_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setup();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// Sound
		mediaWin.release();
		mediaLoss.release();
		mediaWin = null;
		mediaLoss = null;
	}
	
	@Override
	public void onClick(View v)
	{
		//TODO: Implement me!
	}
	
	/**
	 * This methods gets all element references, and
	 * sets all onClickListeners.
	 */
	private void setup()
	{
		// Prepare sounds
		setupMediaPlayers();
		
		// Find element references
		image		= view.findViewById(R.id.galge);
		img_correct = view.findViewById(R.id.image_correct);
		img_wrong	= view.findViewById(R.id.image_wrong);
		txt_view 	= view.findViewById(R.id.txt_word);
		txt_score	= view.findViewById(R.id.txt_score);
		txt_cheat	= view.findViewById(R.id.txt_cheat);
		
		// Set game up
		resetGame();
		resetLayout();
		increaseScore();
	}
	
	/**
	 * This Creates and prepares the two sound for winning and losing.
	 * It uses the MediaPlayers Asynchronous prepare method to avoid
	 * blocking the UI thread.
	 */
	private void setupMediaPlayers()
	{
		// Settings for the MediaPlayers
		AudioAttributes attributes = new AudioAttributes.Builder()
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.setUsage(AudioAttributes.USAGE_GAME)
				.build();
		
		// Create
		try {
			mediaWin 	= new MediaPlayer();
			mediaLoss 	= new MediaPlayer();
			
			mediaWin.setAudioAttributes(attributes);
			mediaLoss.setAudioAttributes(attributes);
			
			// Use correct method depending on Android version
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				mediaWin.setDataSource(getResources().openRawResourceFd(R.raw.win_sound));
				mediaLoss.setDataSource(getResources().openRawResourceFd(R.raw.loss_sound));
			}
			
			else
			{
				mediaWin.setDataSource(getResources().openRawResourceFd(R.raw.win_sound).getFileDescriptor());
				mediaLoss.setDataSource(getResources().openRawResourceFd(R.raw.loss_sound).getFileDescriptor());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Couldn't load sounds");
		}
		
		
		// Asynchronously prepare
		mediaWin.prepareAsync();
		mediaLoss.prepareAsync();
	}
	
	/**
	 * Updates the game state.
	 */
	private void update(Button btn_Clicked)
	{
		// Guess the letter and update score
		logic.gætBogstav(btn_Clicked.getText().toString());
		boolean correct = logic.erSidsteBogstavKorrekt();
		
		if (correct)
		{
			increaseScore();
			animateCorrect(btn_Clicked);
		}
		else
			animateWrong(btn_Clicked);
		
		// Textview update
		txt_view.setText(logic.getSynligtOrd());
		
		// Game done
		if (logic.erSpilletSlut())
		{
			gameDone();
		}
		
		// Game not done
		else
		{
			// Update image
			updateImage();
		}
	}
	
	/**
	 * Makes the correct guess animation.
	 * @param btn_Clicked The clicked button
	 */
	private void animateCorrect(Button btn_Clicked)
	{
		placeImageAtView(btn_Clicked, img_correct);
		
		// Animate
		img_correct.animate()
				.setDuration(550)
				.translationY(-200)
				.alphaBy(-0.8f)
				.start();
	}
	
	/**
	 * Makes the wrong guess animation.
	 * @param btn_Clicked The clicked button
	 */
	private void animateWrong(Button btn_Clicked)
	{
		placeImageAtView(btn_Clicked, img_wrong);
		
		// Animate
		img_wrong.animate()
				.setDuration(550)
				.translationYBy(1100)
				.alphaBy(-0.8f)
				.start();
	}
	
	/**
	 * Places the given ImageView at the given View, and makes the ImageView
	 * visible.
	 * @param view View to place the Image at
	 * @param image Image to place on top of view
	 */
	private void placeImageAtView(View view, ImageView image)
	{
		// Get the location of the button clicked
		int[] location = getViewXYLocation(view);
		
		// Move picture to button and make visible
		image.setVisibility(View.VISIBLE);
		image.setX(location[0]);
		image.setY(location[1]);
		image.setAlpha(1f);
	}
	
	/**
	 * Takes a View component, and gets it's X and Y component. It
	 * returns an array of two. [X, Y]
	 * @param view View object to get location from
	 * @return int array of size 2
	 */
	private int[] getViewXYLocation(View view)
	{
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}
	
	/**
	 * Resets the game.
	 */
	private void resetGame()
	{
		// Reset logic
		logic.nulstil();
	}
	
	/**
	 *
	 */
	private void goMainMenu()
	{
		if (getFragmentManager() != null)
		{
			if (getFragmentManager().getBackStackEntryCount() > 0)
				getFragmentManager().popBackStack();
			else
			{
				getFragmentManager().beginTransaction()
						.replace(R.id.frame, new MainMenuFragment())
						.commit();
			}
		}
		else
		{ System.err.println("ERROR: getFragmentManager() == null"); }
	}
	
	/**
	 * Resets all buttons, text and picture.
	 */
	private void resetLayout()
	{
		// Reset Image
		image.setImageResource(R.drawable.galgevec);
		
		// Reset text
		txt_view.setText(logic.getSynligtOrd());
		
		// Update cheat
		if (settings.isCheatEnabled())
			txt_cheat.setText(logic.getOrdet());
		
		// Reset buttons
		if (buttons != null)
		{
			for (Button btn : buttons)
				btn.setEnabled(true);
		}
		else
			createButtons();
	}
	
	/**
	 * Updates the drawn image.
	 */
	private void updateImage()
	{
		switch (logic.getAntalForkerteBogstaver())
		{
			case 1:
				image.setImageResource(R.drawable.forkert1vec);
				break;
			case 2:
				image.setImageResource(R.drawable.forkert2vec);
				break;
			case 3:
				image.setImageResource(R.drawable.forkert3vec);
				break;
			case 4:
				image.setImageResource(R.drawable.forkert4vec);
				break;
			case 5:
				image.setImageResource(R.drawable.forkert5vec);
				break;
			case 6:
				image.setImageResource(R.drawable.forkert6vec);
				break;
		}
	}
	
	/**
	 * This checks whether last guess was
	 * correct, and then updates the score
	 * if so.
	 */
	@SuppressLint("DefaultLocale")
	private void increaseScore()
	{
		if (logic.erSidsteBogstavKorrekt())
			score++;
		
		// Set the text view
		txt_score.setText( String.format("%s %d", getResources().getString(R.string.score), score) );
	}
	
	/**
	 * Handles logic when game is done.
	 */
	private void gameDone()
	{
		if (logic.erSpilletTabt())
			gameLost();
		else
			gameWon();
	}
	
	/**
	 * Handles logic when game is lost.
	 */
	private void gameLost()
	{
		// Play sound
		mediaLoss.start();
		
		// Set score text
		txt_score.setText(getResources().getString(R.string.tabt));
		
		// Set all buttons inactive
		disableButtons();
		
		// Prompt for saving Highscore
		promptSave();
	}
	
	/**
	 * Handles logic when game is won.
	 */
	private void gameWon()
	{
		// Play sound
		mediaWin.start();
		
		// Update score text
		txt_score.setText(getResources().getString(R.string.sejr));
		
		// Set buttons inactive
		disableButtons();
		
		// Continue new game
		continueGame();
	}
	
	/**
	 * Resets game and layout to start a
	 * new round.
	 */
	private void continueGame()
	{
		// Reset game first and then layout
		resetGame();
		resetLayout();
	}
	
	/**
	 * This method creates the whole keyboard.
	 */
	private void createButtons()
	{
		// Get Table layout and alphabet
		TableLayout layout = view.findViewById(R.id.table);
		String[] alphabet = getResources().getStringArray(R.array.alphabet);
		
		// Constants
		final int LENGTH = alphabet.length;
		final int BUTTON_ROW = getResources().getInteger(R.integer.btnPerRow);
		
		// Create the ArrayList
		buttons = new ArrayList<>(LENGTH);
		
		// Button params
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.weight = 1f;
		
		// Go through all buttons and add them to the table
		TableRow currRow = new TableRow(getContext());
		for (int i=0; i < LENGTH ; i++)
		{
			// If true then create new row
			if (i % BUTTON_ROW == 0)
			{
				layout.addView(currRow);
				currRow = new TableRow(getContext());
			}
			
			// Create button
			Button currButton = new Button(getContext());
			
			// Set button params
			currButton.setText(alphabet[i]);
			currButton.setLayoutParams(params);
			
			// Set buttons onClickListener
			setButtonListener(currButton);
			
			// Add Button to TableRow
			currRow.addView(currButton);
			
			// Add to button list
			buttons.add(currButton);
		}
		
		// Lastly add to TableLayout
		layout.addView(currRow);
	}
	
	/**
	 * Disables all letter buttons on the screen.
	 */
	private void disableButtons()
	{
		for (Button btn : buttons)
			btn.setEnabled(false);
	}
	
	/**
	 * Set the correct onClickListener for the
	 * alphabet letters.
	 * @param btn Button to set
	 */
	private void setButtonListener(final Button btn)
	{
		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Set inactive
				btn.setEnabled(false);
				
				// Update logic
				update(btn);
			}
		});
	}
	
	/**
	 * This launches a dialog for saving the score,
	 * and then saves or don't depending on answer.
	 * This also shifts Fragment.
	 */
	private void promptSave()
	{
		// Inflate view
		final View view = LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_save, null);
		
		// Set description
		TextView textView = view.findViewById(R.id.lossDescription);
		String desc = getResources().getString(R.string.lossDescription);
		textView.setText(String.format(desc, logic.getOrdet(), score));
		
		// Create the alertbox and all it's contents
		AlertDialog dialog = new AlertDialog.Builder(getContext())
				.setTitle(R.string.title_alert)
				.setIcon(R.drawable.save_vec)
				.setView(view)
				.setCancelable(false)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// Get the EditText view
						EditText viewById 	= view.findViewById(R.id.saveName);
						String txt 			= viewById.getText().toString();
						
						//Check if name is entered
						if (txt.isEmpty() || txt.equals(" "))
							Toast.makeText(getContext(), R.string.saveNoName, Toast.LENGTH_LONG).show();
						else {
							// Save the highscore
							saveHighscore(txt);
						}
						
						// Go back to main menu
						goMainMenu();
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// Feedback the user that it isn't saved
						Toast.makeText(getContext(), R.string.not_saved, Toast.LENGTH_SHORT).show();
						// Go back to main menu
						goMainMenu();
					}
				})
				.create();
		dialog.show();
		
		// Set the button colors
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ColorStateList.valueOf(getResources()
		.getColor(R.color.colorPrimary)));
		dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ColorStateList.valueOf(getResources()
				.getColor(R.color.colorPrimary)));
		
	}
	
	/**
	 * Prompt for the name of the player, and save
	 * the highscore on a different thread.
	 * @param name Name of the scorer
	 */
	@SuppressLint("StaticFieldLeak")
	private void saveHighscore(final String name)
	{
		// Save the highscore on different thread
		new AsyncTask<Context, Void, Boolean>()
		{
			@Override
			protected void onPreExecute()
			{
				//TODO: Debug information - remove
				System.out.println("Trying to save Highscore");
			}
			
			@Override
			protected Boolean doInBackground(Context... contexts)
			{
				// Get saving instance
				Save saver = Save.getInstance();
				
				// Get the date and time
				Date date = Calendar.getInstance()
						.getTime();
				
				// Build the highscore
				Highscore high = new Highscore.Builder(score, name)
						.date(date)
						.build();
				
				// Save it
				return saver.saveHighscore(high, contexts[0]);
			}
			
			@Override
			protected void onPostExecute(Boolean bool)
			{
				// Give feedback on save
				if (bool)
					Toast.makeText(getContext(), R.string.saveSuccess, Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getContext(), R.string.saveFail, Toast.LENGTH_SHORT).show();
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getContext());
	}
}
