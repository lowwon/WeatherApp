package com.example.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

public class FragmentMenu extends Fragment {
    View view;
    Button find, follow, lang, color, rate, contact, close;
    ImageView as;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        final FragmentActivity c = getActivity();
        find = view.findViewById(R.id.btnFind);
        follow = view.findViewById(R.id.btnLoveCity);
        lang = view.findViewById(R.id.btnLang);
        color = view.findViewById(R.id.btnColor);
        rate = view.findViewById(R.id.btnRate);
        contact = view.findViewById(R.id.btnContact);
        close = view.findViewById(R.id.btnClose);
//        Glide.with(this).load("file:///android_asset/loading.gif").into(as);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, FindCity.class);
                startActivity(intent);
            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, CityFollow.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
