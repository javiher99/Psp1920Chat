package com.example.Psp1920;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.Psp1920.apibot.ChatterBot;
import com.example.Psp1920.apibot.ChatterBotFactory;
import com.example.Psp1920.apibot.ChatterBotSession;
import com.example.Psp1920.apibot.ChatterBotType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private volatile boolean chat = false;

    private Button btHablar;

    // https://www.bing.com/texamplev3?isVertical=1&&IG=8364837CA9504CCAA5E9067F56AE2B7E&IID=translator.5026.2
    // POST
    // &fromLang=auto-detect&text=Soy%20programador&to=en
    // fromLang: es
    // text: Soy programador
    // to: en

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                chat = !chat;
                if(chat) {
                    new Chat().execute();
                }
            }
        });

        btHablar = findViewById(R.id.again_button);
        btHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Tts.class);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void chat() {
        try {
            ChatterBotFactory factory = new ChatterBotFactory();

            ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
            ChatterBotSession bot1session = bot1.createSession();

            ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();

            String s = "Hi";
            while (chat) {

                System.out.println("bot1> " + s);

                s = bot2session.think(s);
                System.out.println("bot2> " + s);

                s = bot1session.think(s);
            }
        } catch (Exception e){

        }
    }
    // Metodo get
    public static String getTextFromUrl(String src) {
        StringBuffer out = new StringBuffer();
        try {
            URL url = new URL(src);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                out.append(line + "\n");
            }
            in.close();
        } catch (IOException e) {
        }
        return out.toString();
    }

    public static Bitmap getImageFromUrl(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            return null;
        }
    }

    private class Chat extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            chat();
            String s = getTextFromUrl("https://www.google.com/search?q=prueba+de+lo+que+sea&rlz=1C5CHFA_enES870ES870&oq=prueba+de+lo+que+sea&aqs=chrome..69i57j0l5.5490j0j7&sourceid=chrome&ie=UTF-8");
            Log.v("xyz", s);
            return null;
        }
    }
}
