package com.example.galgeleg.fragments.faceOff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galgeleg.R;

import java.util.List;

public class LoaderFragment extends Fragment
{
	private View view;
	private RecyclerView recyclerView;
	
	private List<String> words;
	
	public LoaderFragment(List<String> words)
	{
		this.words = words;
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		return view = inflater.inflate(R.layout.loader_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		// Get elements
		recyclerView = view.findViewById(R.id.recycler);
		
		setup();
	}
	
	private void setup()
	{
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), words,
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_LONG).show();
					}
				});
		
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
				DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(dividerItemDecoration);
		
		recyclerView.setAdapter(adapter);
	}
}
