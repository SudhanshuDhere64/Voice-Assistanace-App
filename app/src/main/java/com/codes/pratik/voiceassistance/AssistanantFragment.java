package com.codes.pratik.voiceassistance;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AssistanantFragment extends Fragment {

    ImageView speakBtn;
    TextView text,micTip;
    SpeechRecognizer recognizer;
    TextToSpeech tts;
    GifImageView gifAssistant;

    public AssistanantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assistanant, container, false);

        //initializing views
        initViews(view);
        //initializing tts;
        initTextToSpeach();

        speakBtn.setOnClickListener(v->{
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                recognizer.startListening(intent);

        });

        recognizeVoice();


        return view;
    }

    private void initViews(View view) {
        speakBtn = view.findViewById(R.id.speak);
        text = view.findViewById(R.id.textView);
        micTip = view.findViewById(R.id.mictip);
        gifAssistant = view.findViewById(R.id.assistantgif);
    }

    private void initTextToSpeach(){
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(tts.getEngines().size() == 0){
                    Toast.makeText(getContext(), "Voice Engine is not available", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Voice Engine Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void recognizeVoice() {
        if(SpeechRecognizer.isRecognitionAvailable(getContext())){
            recognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                    gifAssistant.setVisibility(View.VISIBLE);
                    micTip.setVisibility(View.INVISIBLE);
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