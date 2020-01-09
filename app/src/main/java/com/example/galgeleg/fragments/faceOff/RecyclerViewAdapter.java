package com.example.galgeleg.fragments.faceOff;

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
	
	private List<String> data;
	private LayoutInflater inflater;
	private View.OnClickListener listener;
	
	//endregion
	
	public RecyclerViewAdapter(Context context, List<String> data, View.OnClickListener listener)
	{
		super();
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.listener = listener;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = inflater.inflate(R.layout.loader_recyclerview_row, parent, false);
		view.setOnClickListener(listener);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position)
	{
		holder.textView.setText(data.get(position));
	}
	
	@Override
	public int getItemCount()
	{
		return data.size();
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView textView;
		
		ViewHolder(@NonNull View itemView)
		{
			super(itemView);
			textView = itemView.findViewById(R.id.txt_words);
		}
	}
}
