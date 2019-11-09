package com.example.quikr_dummy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<Map<String, Object>> list;
    private Context context;

    public MyAdapter(Context context, List<Map<String, Object>> list) {
        this.list = list;
        this.context = context;
    }

    @Override

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.text, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        final Map<String,Object> mylist = list.get(position);
        holder.name.setText("Name  :  " + mylist.get("name"));
        holder.price.setText("Price  :  " + mylist.get("price"));
        Log.e("ab",mylist.get("img").toString());
        Picasso
                .get()
                .load(mylist.get("img").toString())
                .into(holder.img);
//        holder.img.setImageBitmap(getBitmapFromURL(mylist.get("img").toString()));
//        holder.ref.setText("Price of one Item:  " + mylist.get(2));

    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try {
            if (list.size() == 0) {

                arr = 0;

            } else {

                arr = list.size();
            }


        } catch (Exception e) {


        }

        return arr;

    }
//@Override
//public int getItemCount() {
//    return list.size();
//}

    class MyHolder extends RecyclerView.ViewHolder {
        TextView ref, name, price;
        CardView cardView;
        ImageView img;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            img = (ImageView)itemView.findViewById(R.id.image);
           // ref = (TextView) itemView.findViewById(R.id.price);
            cardView = (CardView) itemView.findViewById(R.id.card);


        }

    }
}