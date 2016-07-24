package com.modprobe.profit;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatMessageAdapter extends ArrayAdapter<QueryMessage> {
	private final int MY_MESSAGE = 0, OTHER_MESSAGE = 1, MY_IMAGE = 2,
			OTHER_IMAGE = 3, MY_AUDIO = 4, OTHER_AUDIO = 5;
	private Context mContext;
	private File MAIN_DIRECTORY;
	private File IMAGES_DIRECTORY;
	private File AUDIO_DIRECTORY;
	ArrayList<QueryMessage> data;
	public ChatMessageAdapter(Context context, ArrayList<QueryMessage> data) {
		super(context, R.layout.item_mine_message, data);
		mContext = context;
		this.data =  data;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		QueryMessage item = getItem(position);

		if (item.isMine() && item.type() == Constants.TEXT)
			return MY_MESSAGE;
		
			return OTHER_MESSAGE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int viewType = getItemViewType(position);
		if (viewType == MY_MESSAGE) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_mine_message, parent, false);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(getItem(position).getContent());

		} else if (viewType == OTHER_MESSAGE) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_other_message, parent, false);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(getItem(position).getContent());
		} 

		return convertView;
	}
}
