package com.example.guswn_000.a170601inclass;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main4Activity extends AppCompatActivity
{
    TextView tv;
    EditText etid,etpw;
    String userid,password;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setTitle("실습4");
        tv = (TextView)findViewById(R.id.tv);
        etid = (EditText)findViewById(R.id.etid);
        etpw = (EditText)findViewById(R.id.etpw);
        btn = (Button)findViewById(R.id.button4);

    }

    Handler handler = new Handler();
    class myThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                String postData = "userid="+ URLEncoder.encode(userid) + "&password=" + URLEncoder.encode(password);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    inputStream = httpURLConnection.getInputStream();
                }
                else
                {
                    inputStream = httpURLConnection.getErrorStream();
                }
                final String result = loginResult(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(result.equals("FAIL"))
                        {
                            tv.setText("로그인에 실패했습니다");
                        }
                        else
                        {
                            tv.setText(result + "님 로그인 성공");
                        }
                    }
                });

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    String loginResult(InputStream is)
    {
        String data = "";
        Scanner s = new Scanner(is);
        while (s.hasNext()) data += s.nextLine();
        s.close();
        return data;
    }


    public void onClick(View v)
    {
        myThread thread = new myThread();
        if(v.getId() == R.id.button4)
        {
            if(btn.getText().toString().equals("LOGIN"))
            {
                userid = etid.getText().toString().trim();
                password = etpw.getText().toString().trim();
                thread.start();
            }

        }

    }


}
