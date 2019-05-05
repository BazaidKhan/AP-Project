package com.example.lenovo.ap2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Item> modellist;
    private ArrayList<Item> ItemList;
    Bitmap bitmap;

    public  HomeAdapter(Context context, ArrayList<Item> list) {
        mContext = context;
        this.ItemList = list;
        inflater = LayoutInflater.from(mContext);
        this.modellist = new ArrayList<Item>();
        this.modellist.addAll(list);
    }
    static class ViewHolder{
        TextView Name;
        TextView Price;
        ImageView Image;
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
        Item currentShirt= ItemList.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.homelist, null, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Name =
                    (TextView) convertView.findViewById(R.id.NAME);
            viewHolder.Price =
                    (TextView) convertView.findViewById(R.id.Price);
            viewHolder.Image =
                    (ImageView) convertView.findViewById(R.id.Image);

            convertView.setTag(viewHolder);
        }

        TextView Name =
                ((ViewHolder) convertView.getTag()).Name;
        TextView Price=
                ((ViewHolder) convertView.getTag()).Price;
        ImageView ContactImage =
                ((ViewHolder) convertView.getTag()).Image;

        Name.setText(currentShirt.Name);
        Price.setText(currentShirt.Price.toString()+"$");

        String imageUri=currentShirt.ImageURL;
        Picasso.with(mContext).load(imageUri).into(ContactImage);

        return convertView;
    }
};
