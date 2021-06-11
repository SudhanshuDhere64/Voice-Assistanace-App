package com.codes.pratik.voiceassistance;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AssistanantFragment extends Fragment {

    private static final String TAG = "TAG" ;
    ImageView speakBtn,logoPng,logoPng1;
    TextView text,micTip;
    SpeechRecognizer recognizer;
    SpeechRecognizer callRec;
    TextToSpeech tts;
    GifImageView gifAssistant;
    ArrayList<String> contactName;
    ArrayList<String> contactNo;
    String location,brain;
    String remember = "remember";

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

        initTextToSpeech();
        location="pune";
        brain = "no data available";

        speakBtn.setOnClickListener(v->{
                listen();
                logoPng.setVisibility(View.INVISIBLE);
                logoPng1.setVisibility(View.VISIBLE);
                gifAssistant.setVisibility(View.VISIBLE);

        });
        recognizeVoice();
        return view;
    }

    private void listen() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizer.startListening(intent);
    }

    private void initViews(View view) {
        speakBtn = view.findViewById(R.id.speak);
        text = view.findViewById(R.id.textView);
        micTip = view.findViewById(R.id.mictip);
        gifAssistant = view.findViewById(R.id.assistantgif);
        logoPng = view.findViewById(R.id.logopng);
        logoPng1 = view.findViewById(R.id.logopng2);
    }

    private void initTextToSpeech(){
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

                    String command = result.get(0).toLowerCase();

                    text.setText(result.get(0));
                    //speak(result.get(0));

                    if(command.contains("google")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")));

                    }
                    else if (command.contains("mail")){

                        Intent mail = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse("mailto:?subject="+"Mail from Voice Assistance App"+ "&body="+""+"&to="+"");
                        mail.setData(data);
                        startActivity(Intent.createChooser(mail,"Send Email to.."));

                    }
                    else if (command.contains("youtube")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                        if (launchIntent != null) {
                            speak("Launching Youtube");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("setting")){
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                    else if (command.contains("chrome")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.chrome");
                        if (launchIntent != null) {
                            speak("Launching Chrome");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("maps")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.gsm.maps");
                        if (launchIntent != null) {
                            speak("Launching Maps");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("drive")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.docs");
                        if (launchIntent != null) {
                            speak("Launching Drive");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("photos") || command.contains("gallery")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.photos");
                        if (launchIntent != null) {
                            speak("Launching Photos");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("store")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.android.vending");
                        if (launchIntent != null) {
                            speak("Launching Play Store");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("facebook")||command.contains("fb")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                        if (launchIntent != null) {
                            speak("Launching Facebook");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("camera")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.android.camera");
                        if (launchIntent != null) {
                            speak("Launching camera");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("whatsapp")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                        if (launchIntent != null) {
                            speak("Launching whatsapp");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("insta")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                        if (launchIntent != null) {
                            speak("Launching instagram");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("snapchat")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.snapchat.android");
                        if (launchIntent != null) {
                            speak("Launching snapchat");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("telegram")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                        if (launchIntent != null) {
                            speak("Launching telegram");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("phonepe")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.phonepe.app");
                        if (launchIntent != null) {
                            speak("Launching Phonepay");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("gpay") || command.contains("google pay")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
                        if (launchIntent != null) {
                            speak("Launching Google pay");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("paytm")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                        if (launchIntent != null) {
                            speak("Launching paytm");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("spotify")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.spotify.music");
                        if (launchIntent != null) {
                            speak("Launching spotify");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("truecaller")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.truecaller");
                        if (launchIntent != null) {
                            speak("Launching truecaller");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("airtel")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.myairtelapp");
                        if (launchIntent != null) {
                            speak("Launching My Airtel");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("amazon")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.amazon.mShop.android.shopping");
                        if (launchIntent != null) {
                            speak("Launching Amazon");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("jio")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.jio.myjio");
                        if (launchIntent != null) {
                            speak("Launching My jio");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("linkedin")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.linkedin.android");
                        if (launchIntent != null) {
                            speak("Launching LinkedIn");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("picsart")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.picsart.studio");
                        if (launchIntent != null) {
                            speak("Launching picsart");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("classroom")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.classroom");
                        if (launchIntent != null) {
                            speak("Launching classroom");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }
                    else if (command.contains("calculator")){
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.sec.android.app.popupcalculator");
                        if (launchIntent != null) {
                            speak("Launching calculator");
                            startActivity(launchIntent);
                        } else {
                            speak("There is no app available");
                        }
                    }else if (command.contains("call")){
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+"8530899088"));//change the number
                        startActivity(callIntent);
                    }else if (command.contains("message")){
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("+918530899088", null, "hi", null, null);
                            Toast.makeText(getContext(), "Message Sent",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(),ex.getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }
                    }else if (command.contains("location")){
                       speak("Your Current Location is"+location);
                    }
                    else if(command.contains(remember)){
                        brain = command;
                        remember ="norem";
                        speak("Okay");
                    }else if (command.contains("remember") && command.contains("what")){
                        speak("you told me to"+brain);
                    }
                    //basic chat
                    else if (command.contains("hey") || command.contains("hi")){
                        speak("hey");
                    }
                    else if (command.contains("how are you") || command.contains("are you fine")){
                        speak("I am fine");
                    }
                    else if (command.contains("who are you") || command.contains("what is your name")){
                        speak("I am your voice assistant");
                    }
                    else if (command.contains("joke")){
                        speak("your life, hahahaha!");
                    }
                    else if (command.contains("designs") || command.contains("developed") || command.contains("created")){
                        speak("Pratik created me");
                    }
                    else if (command.contains("what can you do") || command.contains("functions")){
                        speak("I can do many things. such as, automating your smartphone, remember something for you, I can make calls for you as well as messages. and many more things");

                    }
                    else if (command.contains("morning")){
                        speak("hey, Good Morning");
                    }
                    else if (command.contains("afternoon")){
                        speak("hey, Good Afternoon");
                    }
                    else if (command.contains("on bluetooth")){
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBluetoothAdapter.isEnabled()) {
                            mBluetoothAdapter.enable();
                            speak("Bluetooth turned on");
                        }
                    }
                    else if (command.contains("off bluetooth")){
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter.isEnabled()) {
                            mBluetoothAdapter.disable();
                            speak("Bluetooth turned off");
                        }
                    }
                    else if (command.contains("music")){
                        MediaPlayer mediaPlayer;
                        try {
                            String PATH_TO_FILE = "/sdcard/music.mp3";
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(PATH_TO_FILE);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        }catch (Exception e){
                            speak("Can't play music");
                        }
                    }else {
                        speak("Sorry, I didn't understand");
                    }



                    result.clear();
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