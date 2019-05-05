package com.example.lenovo.ap2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

public class WishListAdapter extends BaseAdapter {
    public static final String EXTRA_MESSAGE = "";
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Item> modellist;
    private ArrayList<Item> ItemList;
    private ArrayList<Item> OriginalList;
    String Size;
    int indexofCurrent=0;
    String Email;

    public  WishListAdapter(Context context, ArrayList<Item> list) {
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
        ImageView Cross;
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
        final Item currentShirt= ItemList.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.cartlist, null, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Name =
                    (TextView) convertView.findViewById(R.id.cartNAME);
            viewHolder.Price =
                    (TextView) convertView.findViewById(R.id.cartPrice);
            viewHolder.Image =
                    (ImageView) convertView.findViewById(R.id.cartImage);
            viewHolder.Cross =
                    (ImageView) convertView.findViewById(R.id.cartRemove);


            convertView.setTag(viewHolder);
        }
        ImageView b2= ((ViewHolder) convertView.getTag()).Cross;;
        b2.setTag(position);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int pos = (int)arg0.getTag();
                ItemList.remove(pos);
                WishListAdapter.this.notifyDataSetChanged();
                check();
            }
        });



        ImageView b3=((ViewHolder) convertView.getTag()).Image;;
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail(currentShirt);
            }
        });

        TextView Name =
                ((ViewHolder) convertView.getTag()).Name;
        TextView Price=
                ((ViewHolder) convertView.getTag()).Price;
        ImageView ContactImage =
                ((ViewHolder) convertView.getTag()).Image;
        ImageView Cross =
                ((ViewHolder) convertView.getTag()).Cross;

        Name.setText(currentShirt.Name);
        Price.setText(currentShirt.Price.toString());

        String imageUri = currentShirt.ImageURL;
        Picasso.with(mContext).load(imageUri).into(ContactImage);

        String cross="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/High-contrast-dialog-close.svg/600px-High-contrast-dialog-close.svg.png";
        Picasso.with(mContext).load(cross).into(Cross);

            /*String pathToFile = currentShirt.image;
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(ContactImage);
            downloadTask.execute(pathToFile);*/
        return convertView;
    }
    public void updateDetail(Item temp) {
        Intent intent=new Intent(mContext,Detail.class);
        Toast.makeText(mContext,"Moving",Toast.LENGTH_LONG).show();
        intent.putExtra("item",temp);
        mContext.startActivity(intent);
    }

    public void check ()
    {
    }
    public void oldList()
    {

        Toast.makeText(mContext,"Removed from Wishlist",Toast.LENGTH_SHORT).show();
        Wishlist current=new Wishlist();

    }
};
