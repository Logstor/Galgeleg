package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galgeleg.Logic.Galgelogik;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	// Logic
	private Galgelogik logic = Galgelogik.getInstance();
	
	// Elements
	private TextView txt_word;
	private EditText txt_guess;
	private ImageView img_galge;
	private Button btn_guess;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setup();
	}
	
	protected void setup()
	{
		// Get elements
		txt_word 	= findViewById(R.id.txt_word);
		txt_guess 	= findViewById(R.id.txt_guess);
		img_galge 	= findViewById(R.id.galge);
		btn_guess	= findViewById(R.id.guess);
		
		// Listeners
		btn_guess.setOnClickListener(this);
		
		// Set word
		logic.nulstil();
		txt_word.setText(logic.getSynligtOrd());
	}
	
	protected void update()
	{
		// Textview update
		txt_word.setText(logic.getSynligtOrd());
	}
	
	@Override
	public void onClick(View v)
	{
		if (v==btn_guess)
		{
		
		}
	}
}
