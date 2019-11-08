package com.example.galgeleg.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galgeleg.Logic.Galgelogik;
import com.example.galgeleg.R;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment implements View.OnClickListener
{
	private View view;
	private ImageView image;
	private TextView txt_view, txt_score;
	private List<Button> buttons;
	private int score = 0;
	
	private Galgelogik logic = Galgelogik.getInstance();
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return view = inflater.inflate(R.layout.fragment_game, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setup();
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
		// Find element references
		image		= view.findViewById(R.id.galge);
		txt_view 	= view.findViewById(R.id.txt_word);
		txt_score	= view.findViewById(R.id.txt_score);
		
		// Set game up
		resetGame();
		resetLayout();
		updateScore();
	}
	
	/**
	 * Updates the game state.
	 */
	private void update(Button btn_Clicked)
	{
		// Guess the letter and update score
		logic.gætBogstav(btn_Clicked.getText().toString());
		updateScore();
		
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
	 * Resets the game.
	 */
	private void resetGame()
	{
		// Reset logic
		logic.nulstil();
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
	private void updateScore()
	{
		// If last was correct, then add to score
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
		// Set score text
		txt_score.setText(getResources().getString(R.string.tabt));
		
		// Set all buttons inactive
		disableButtons();
		
		// Save Highscore
		
		
		// Go back to main menu
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
		{
			System.err.println("ERROR: getFragmentManager() == null");
		}
	}
	
	/**
	 * Handles logic when game is won.
	 */
	private void gameWon()
	{
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
}
