package com.example.galgeleg.fragments.mainMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class MainMenuButton extends AppCompatButton
{
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
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			animate()
					.alpha(0.5f)
					.setDuration(100)
					.start();
		
		else if (event.getAction() == MotionEvent.ACTION_UP)
			animate()
					.alpha(1f)
					.setDuration(100)
					.start();
			
		return super.onTouchEvent(event);
	}
}
