package com.example.guswn_000.a170601inclass;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity
{
    String SERVER_IP = "172.17.67.116"; //cmd에서 ipconfig에서 뜨는 무선랜어댑터와이파이 - ipv4주소
    int SERVER_PORT = 200;
    String msg = ""; //보낼 스트링
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.etmsg);
    }
    Handler myHandler = new Handler();
    Thread myThread = new Thread(){
        @Override
        public void run()
        {
            try
            {
                Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);
//                System.out.println("Client] 서버 접속");

                //서버에 보낼 데이터


                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(msg);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                final Object obj = instream.readObject();
                myHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(),(String)obj, Toast.LENGTH_SHORT).show();

                    }
                });

                aSocket.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    };


    public void onClick(View v)
    {
        msg = e1.getText().toString();
        myThread.start();
    }

}
