package com.example.quikr_dummy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class items extends AppCompatActivity {
    String nam,des,pric,im,vi;
    TextView name,desc,price;
    ImageView img;
    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Intent intent=getIntent();
        nam=intent.getStringExtra("name");
        des=intent.getStringExtra("desc");
        pric=intent.getStringExtra("price");
        im=intent.getStringExtra("img");
        vi=intent.getStringExtra("vid");
        name=findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        img=findViewById(R.id.image);
        video=findViewById(R.id.video);
        fill();
    }

    public void fill()
    {
        name.setText("Name: "+nam);
        desc.setText("Description: "+desc);
        price.setText("Price: "+pric);
        Picasso
                .get()
                .load(im)
                .into(img);
        video.setVideoPath(vi);
        video.start();

    }

}
