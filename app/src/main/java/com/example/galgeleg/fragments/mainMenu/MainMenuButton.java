package com.example.galgeleg.fragments.mainMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class MainMenuButton extends AppCompatButton
{
	private final int animateTime 	= 75;
	private final float alpha 		= 0.3f;
	
	//region Constructors
	
	public MainMenuButton(Context context)
	{
		super(context);
	}
	
	public MainMenuButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public MainMenuButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}
	
	//endregion
	
	@Override
	public boolean performClick()
	{ return super.performClick(); }
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		/*
		 * Animating the alpha value. It performs the click, when the user
		 * lifts the finger.
		 */
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			animate()
					.alpha(alpha)
					.setDuration(animateTime)
					.start();
		
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			animate()
					.alpha(1f)
					.setDuration(animateTime)
					.start();
			performClick();
		}
		
		return true;
	}
}
