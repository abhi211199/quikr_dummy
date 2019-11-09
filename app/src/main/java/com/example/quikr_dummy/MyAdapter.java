package com.example.quikr_dummy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String id;

    public MyAdapter(Context context, List<Map<String, Object>> list, String id) {
        this.list = list;
        this.context = context;
        this.id = id;
    }


    @Override

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.text,parent,false);
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Log.e("aa",id);
//                                                   Toast.makeText(context,mylist.get("comments").toString(),Toast.LENGTH_LONG).show();
                                                   Intent intent = new Intent(context,items.class);
                                                   intent.putExtra("id",id);
                                                   intent.putExtra("name",mylist.get("name").toString());
                                                   intent.putExtra("price",mylist.get("price").toString());
                                                   intent.putExtra("desc",mylist.get("desc").toString());
                                                   intent.putExtra("img",mylist.get("img").toString());
                                                   intent.putExtra("vid",mylist.get("vid").toString());
                                                   intent.putExtra("comments",mylist.get("comments").toString());
                                                   context.startActivity(intent);
                                               }
                                           });
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
        LinearLayout main;

        public MyHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            img = (ImageView)itemView.findViewById(R.id.image);
           // ref = (TextView) itemView.findViewById(R.id.price);
            cardView = (CardView) itemView.findViewById(R.id.text);
//            main = (LinearLayout) itemView.findViewById(R.id.main);
//            main.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("aa","aa");
//                  //  Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
////                    if(clicklistener !=null){
////                        clicklistener.itemClicked(v,getAdapterPosition());
////                    }
//                }
//            });


        }

//        @Override
//        public void onClick(View v) {
//            Log.e("aa","aa");
//        }
    }

}