package com.example.phareaon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPhare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phare);

        Intent intent = getIntent();
        String name = String.valueOf(((TextView)findViewById(R.id.nomPhare)).getText());
        String region = String.valueOf(((TextView)findViewById(R.id.regionPhare)).getText());
        String construction = String.valueOf(((TextView)findViewById(R.id.constructionPhare)).getText());
        String lattitude = String.valueOf(((TextView)findViewById(R.id.lattitudePhare)).getText());
        String longitude = String.valueOf(((TextView)findViewById(R.id.longitudePhare)).getText());
        String clignotement = String.valueOf(((TextView)findViewById(R.id.clignotementPhare)).getText());
        String filename = intent.getStringExtra("imagePhare");

        Resources resources = MainActivity.getContext().getResources();
        final int drawableID = resources.getIdentifier(filename, "drawable",
                MainActivity.getContext().getPackageName());

        name += intent.getStringExtra("nomPhare");
        region += intent.getStringExtra("regionPhare");
        construction += intent.getStringExtra("constructionPhare");
        lattitude += intent.getStringExtra("lattitudePhare");
        longitude += intent.getStringExtra("longitudePhare");
        clignotement += intent.getStringExtra("clignotementPhare") + " ms";

        super.setTitle(intent.getStringExtra("nomPhare"));
        ((ImageView)findViewById(R.id.imagePhare)).setImageDrawable(getDrawable(drawableID));
        ((TextView)findViewById(R.id.nomPhare)).setText(name);
        ((TextView)findViewById(R.id.regionPhare)).setText(region);
        ((TextView)findViewById(R.id.constructionPhare)).setText(construction);
        ((TextView)findViewById(R.id.lattitudePhare)).setText(lattitude);
        ((TextView)findViewById(R.id.longitudePhare)).setText(longitude);
        ((TextView)findViewById(R.id.longitudePhare)).setText(longitude);
        ((TextView)findViewById(R.id.clignotementPhare)).setText(clignotement);
    }
}
