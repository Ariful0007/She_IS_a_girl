package com.example.she_is_a_girl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.she_is_a_girl.ChatActivity;
import com.example.she_is_a_girl.Models.Tamim;
import com.example.she_is_a_girl.R;

import java.util.List;

public class TamimAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Tamim> tamims;
    public  TamimAdapter(Activity mContext,List<Tamim>tamims)
    {
        super(mContext, R.layout.row_user,tamims);
        this.mContext=mContext;
        this.tamims=tamims;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View Listview= inflater.inflate(R.layout.row_user,null,true);
        String key=tamims.get(position).getUid();

        TextView name=Listview.findViewById(R.id.username);
        Tamim tamim=tamims.get(position);
        name.setText(tamim.getName());
        Intent intent=new Intent(mContext, ChatActivity.class);
        intent.putExtra("UId: ",key);
        mContext.startActivity(intent);
        return  Listview;

    }
}
