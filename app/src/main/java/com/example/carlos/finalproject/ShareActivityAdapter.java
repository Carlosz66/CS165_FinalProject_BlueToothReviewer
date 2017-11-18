package com.example.carlos.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlos on 17/11/16.
 */

public class ShareActivityAdapter extends ArrayAdapter<ActivityInfo> {
    private int resourceId;

    public ShareActivity delegate;

    public ShareActivityAdapter(Context context, int textViewResourceId,
                          List<ActivityInfo> objects) {
        super(context, textViewResourceId, objects);
        delegate= (ShareActivity) context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyListener myListener=null;
        myListener=new MyListener(position);

        ActivityInfo activityDes = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView actDes = view.findViewById(R.id.shareActivityInCell);
        Button shaButton = view.findViewById(R.id.shareButtonInCell);

        actDes.setText("Activity: "+activityDes.actName +'\n'+"Location: "+activityDes.locName+'\n'+"Time: "+activityDes.actTime);
        shaButton.setOnClickListener(myListener);

        return view;
    }

    private class MyListener implements View.OnClickListener {
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
                Log.d("ShareActivityItem","Click " + mPosition +" button");
                delegate.shareActivityTo(mPosition);

        }

    }


}
