package com.example.chalkboardnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getstartedbutton;
    Animation topanim,bottomanim,leftanim;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        leftanim= AnimationUtils.loadAnimation(this,R.anim.left_anim);
       img = findViewById(R.id.img);
        //img.setAnimation(topanim);
        getstartedbutton = findViewById(R.id.getstarted);
        //getstartedbutton.setAnimation(bottomanim);
        getstartedbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        openActivity2();
    }

    private void openActivity2() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
