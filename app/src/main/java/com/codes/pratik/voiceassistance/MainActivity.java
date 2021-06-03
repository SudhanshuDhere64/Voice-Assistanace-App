package com.codes.pratik.voiceassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button speakBtn;
    TextView text;
    SpeechRecognizer recognizer;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing views
        initViews();
        //initializing tts;
        initTextToSpeach();

        speakBtn.setOnClickListener(v->{

            Intent intent =  new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
            recognizer.startListening(intent);
        });

        recognizeVoice();
    }

    private void initViews() {
        speakBtn = findViewById(R.id.speak);
        text = findViewById(R.id.textView);
    }

    private void initTextToSpeach(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(tts.getEngines().size() == 0){
                    Toast.makeText(MainActivity.this, "Voice Engine is not available", Toast.LENGTH_SHORT).show();
                }else{
                    speak("Hey, Your Voice assistant is here");
                }
            }
        });
    }

    void speak(String command){
        try {
            tts.speak(command, TextToSpeech.QUEUE_FLUSH, null, null);
        }catch (Exception e){
            Toast.makeText(this, "Voice Engine Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void recognizeVoice() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {

                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    text.setText(result.get(0));
                    speak(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }
}