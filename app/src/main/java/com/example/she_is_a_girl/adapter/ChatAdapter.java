package com.example.she_is_a_girl.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter {
    class  MyHolder extends RecyclerView.ViewHolder
    {
        TextView message,time,isSeen;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //message=itemView.findViewById(R.id.)
        }
    }
}
