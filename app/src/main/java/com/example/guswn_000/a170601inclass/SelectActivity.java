package com.example.guswn_000.a170601inclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.button)
        {
            Intent intent = new Intent(SelectActivity.this,Main2Activity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button2)
        {
            Intent intent = new Intent(SelectActivity.this,Main3Activity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button3)
        {
            Intent intent = new Intent(SelectActivity.this,Main4Activity.class);
            startActivity(intent);
        }
    }
}
