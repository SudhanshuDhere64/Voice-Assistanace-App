package com.codes.pratik.voiceassistance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends Fragment {


    TextView mailPk,gitPk,folioPk;
    TextView mailSd,gitSd,folioSd;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        mailPk = view.findViewById(R.id.mailtxt);
        gitPk = view.findViewById(R.id.gitTxt);
        folioPk = view.findViewById(R.id.folioTxt);

        mailSd = view.findViewById(R.id.mailtxt1);
        gitSd = view.findViewById(R.id.gitTxt1);
        folioSd = view.findViewById(R.id.folioTxt1);

        mailPk.setOnClickListener(v->{
            Intent mail = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject="+"Mail from Voice Assistance App"+ "&body="+""+"&to="+"pratikrkate@gmail.com");
            mail.setData(data);
            startActivity(Intent.createChooser(mail,"Send Email to us.."));
        });

        gitPk.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/pratik-kate/"));
            startActivity(browserIntent);
        });

        folioPk.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pratik-kate.github.io/"));
            startActivity(browserIntent);
        });

        //
        mailSd.setOnClickListener(v->{
            Intent mail = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject="+"Mail from Voice Assistance App"+ "&body="+""+"&to="+"sudhashudhere64@gmail.com");
            mail.setData(data);
            startActivity(Intent.createChooser(mail,"Send Email to us.."));
        });

        gitSd.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sudhanshudhere64/"));
            startActivity(browserIntent);
        });

        folioSd.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sudhanshudhere64.github.io/"));
            startActivity(browserIntent);
        });
        return  view;
    }
}