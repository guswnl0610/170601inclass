package com.example.guswn_000.a170601inclass;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity
{
    String urlstr;
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("실습2");
        e1 = (EditText)findViewById(R.id.eturl);
        e2 = (EditText)findViewById(R.id.etdata);
    }

    Handler myhandler = new Handler();
    Thread mythread = new Thread()
    {
        @Override
        public void run()
        {
            super.run();
            try
            {
                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    final String data = readData(urlConnection.getInputStream());

                    myhandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            e2.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public String readData(InputStream is)
    {
        String data ="";
        Scanner s = new Scanner(is);
        while (s.hasNext())
        {
            data += s.nextLine();
        }
        s.close();
        return data;
    }

    public void onClick(View v)
    {
        urlstr = e1.getText().toString();
        mythread.start();
    }

}
