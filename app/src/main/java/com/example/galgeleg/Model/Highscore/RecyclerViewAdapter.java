package com.example.galgeleg.Model.Highscore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galgeleg.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
	//region Fields
	
	private List<Highscore> data;
	private LayoutInflater inflater;
	
	//endregion
	
	public RecyclerViewAdapter(Context context, List<Highscore> data)
	{
		super();
		this.data = data;
		this.inflater = LayoutInflater.from(context);
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = inflater.inflate(R.layout.highscore_recyclerview_row, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position)
	{
		// Get the Highscore object
		Highscore score = data.get(position);
		
		@SuppressLint("DefaultLocale")
		String text = String.format("Score: %d", score.getScore());
		
		// Set the text
		holder.txt_score.setText(text);
		holder.txt_name.setText(score.getName());
	}
	
	@Override
	public int getItemCount()
	{
		return data.size();
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView txt_score, txt_name;
		
		ViewHolder(@NonNull View itemView)
		{
			super(itemView);
			txt_score = itemView.findViewById(R.id.txt_score);
			txt_name = itemView.findViewById(R.id.txt_name);
		}
	}
}
