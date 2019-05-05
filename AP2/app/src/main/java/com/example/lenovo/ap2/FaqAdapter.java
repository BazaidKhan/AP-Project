package com.example.lenovo.ap2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FaqAdapter extends BaseAdapter {
    public static final String EXTRA_MESSAGE = "";
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<fq> modellist;
    private ArrayList<fq> ItemList;
    String Size;
    int indexofCurrent=0;
    String Email;

    public FaqAdapter(Context context, ArrayList<fq> list) {
        mContext = context;
        this.ItemList = list;
        inflater = LayoutInflater.from(mContext);
        this.modellist = new ArrayList<fq>();
        this.modellist.addAll(list);

    }
    static class ViewHolder{
        TextView Quesiton;
        TextView Username;
    }
    @Override
    public int getCount() {
        return ItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return ItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final fq currentShirt= ItemList.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.faqlist, null, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Quesiton =
                    (TextView) convertView.findViewById(R.id.cartNAME);
            viewHolder.Username =
                    (TextView) convertView.findViewById(R.id.cartPrice);
            convertView.setTag(viewHolder);
        }

        TextView Question =
                ((ViewHolder) convertView.getTag()).Quesiton;
        TextView Username=
                ((ViewHolder) convertView.getTag()).Username;

        Question.setText(currentShirt.Question);
        Username.setText(currentShirt.UserId.toString());
        return convertView;
    }
};
