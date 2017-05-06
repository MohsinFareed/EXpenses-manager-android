package com.example.ali.xpensemangertypeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class Main2Activity extends AppCompatActivity {
    int idno = 0;
   static boolean notedited = true;
    EditText value;
    ArrayList <String > loadedlist;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        idno = getIntent().getIntExtra("id",-1);
        value = (EditText)findViewById(R.id.userinput);
        Log.d("Mainactivity2","My id item is"+ idno);
        loadedlist = MainActivity.noteList;
//        if(idno != -1)
//        {
//
//          value.setText(MainActivity.noteList.get(idno));
//
//        }

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notedited = true;
                if(idno-1==MainActivity.noteList.size()-1)
               MainActivity.noteList.add(idno,String.valueOf(s));
                else
                    MainActivity.noteList.set(idno,String.valueOf(s));
                if(MainActivity.s == null)
                {
                    MainActivity.s = new HashSet<String>();

                }
                else
                MainActivity.s.clear();
                MainActivity.s.addAll(MainActivity.noteList);
sp.edit().putStringSet("notelist",MainActivity.s).apply();
                MainActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

}
