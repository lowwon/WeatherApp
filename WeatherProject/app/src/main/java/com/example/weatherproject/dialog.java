package com.example.weatherproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class dialog {
    private Activity activity;
    private AlertDialog dialog;
    dialog(Activity myActivity) {
        activity = myActivity;
    }
    @SuppressLint("InflateParams")
    void startContactdialog(){
        ImageView f1,f2,f3,f4,f5;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View views = inflater.inflate(R.layout.contact, null);
        Context context = views.getContext();
        f1 = views.findViewById(R.id.img1);
        f2 = views.findViewById(R.id.img2);
        f3 = views.findViewById(R.id.img3);
        f4 = views.findViewById(R.id.img4);
        f5 = views.findViewById(R.id.img5);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mr.united02/")));
            }
        });
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100008289520377")));
            }
        });
        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tttt555333333")));
            }
        });
        f4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mon.2k1")));
            }
        });
        f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/m.blt.951")));
            }
        });
        builder.setView(views);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }
    void startLoadingdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void success(Object a, int check) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View views = inflater.inflate(R.layout.success_load, null);
        TextView txt = views.findViewById(R.id.idSuccess);
        txt.setText(a.toString());
        Button back = views.findViewById(R.id.backTo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check == 1)
                    System.exit(0);
                else
                    dialog.dismiss();
            }
        });
        builder.setView(views);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void fail(String a) {
        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View views = inflater.inflate(R.layout.error_load, null);
        Button back = views.findViewById(R.id.backTo);
        TextView txt = views.findViewById(R.id.idError);
        txt.setText(a);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        builder.setView(views);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void dismissdialog() {
        dialog.dismiss();
    }
}
