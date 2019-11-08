package com.example.galgeleg.Fragments;

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
	private ImageView image;
	private TextView txt_view;
	private List<Button> buttons;
	
	private Galgelogik logic = Galgelogik.getInstance();
	
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
	
	@Override
	public void onClick(View v)
	{
		//TODO: Implement me!
	}
	
	/**
	 * This methods gets all element references, and
	 * sets all onClickListeners.
	 * @param view from onViewCreated
	 */
	private void setup(View view)
	{
		// Create all buttons
		createButtons(view);
		
		// Find element references
		image		= view.findViewById(R.id.galge);
		txt_view 	= view.findViewById(R.id.txt_word);
		
		// Set game up
		reset();
	}
	
	/**
	 * Updates the game state.
	 */
	private void update(Button btn_Clicked)
	{
		// Guess the letter
		logic.gætBogstav(btn_Clicked.getText().toString());
		
		// Textview update
		txt_view.setText(logic.getSynligtOrd());
		
		// Game not done
		if (!logic.erSpilletSlut())
		{
			// Update image
			updateImage();
		}
		// Game done
		else
		{
		
		}
	}
	
	/**
	 * Resets the game.
	 */
	private void reset()
	{
		// Reset logic
		logic.nulstil();
		// Reset Image
		image.setImageResource(R.drawable.galgevec);
		// Reset text
		txt_view.setText(logic.getSynligtOrd());
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
	 * This method creates the whole keyboard.
	 */
	private void createButtons(View view)
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
