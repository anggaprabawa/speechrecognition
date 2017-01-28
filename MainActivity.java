package com.example.anggaprabawa.speaksscheduler;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private TextView txtSpeechOutput;
    private Button btnSpeechOut;
    private Button btnSpeechIn;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String tanggapan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtSpeechOutput = (TextView) findViewById(R.id.txtSpeechOutput);
        btnSpeechIn = (Button) findViewById(R.id.btnSpeechIn);
        btnSpeechIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnSpeechIn) {
                    promptSpeechInput();
                }
            }
        });
        btnSpeechOut = (Button) findViewById(R.id.btnSpeechOut);
        btnSpeechOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnSpeechOut) {
                    getSpeechOutput();
                }
            }
        });
        txtSpeechOutput.setText(tanggapan);
        getSpeechOutput();
    }


    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Katakan sesuatu...");
    try
    {
        startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
    }
    catch (ActivityNotFoundException a)
    {
        Toast.makeText(getApplicationContext(),
                "Maaf, perangkat anda tidak support terhadap speech input",
                Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    SpeechInput();
                }
                break;
            }
        }

    }

    private void SpeechInput() {

        final String pertanyaan = txtSpeechInput.getText().toString().trim();

        class SpeechInput extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground (Void...v){
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.SPEECHINPUT, pertanyaan);

                RequestHandler rh = new RequestHandler();
                String rslt = rh.sendPostRequest(Config.URL_SPEECHINPUT, params);
                return rslt;
            }
        }
        SpeechInput speechinput = new SpeechInput();
        speechinput.execute();
    }



    private void getSpeechOutput () {
        class GetSpeechOutput extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                SpeechOutput(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_SPEECHOUTPUT, tanggapan);
                return s;
            }
        }
        GetSpeechOutput getspeechoutput = new GetSpeechOutput();
        getspeechoutput.execute();
    }

    private void SpeechOutput(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                JSONObject c = result.getJSONObject(0);
                String tanggapan = c.getString(Config.TAG_SPEECHOUTPUT);
                txtSpeechOutput.setText(tanggapan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}