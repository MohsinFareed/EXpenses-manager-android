package com.example.ali.xpensemangertypeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
   static Set <String> s ;
    SharedPreferences sp;

  static   ArrayList <String> noteList  = new ArrayList<String>();
   static ArrayAdapter<String> arrayAdapter;
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
sp = this.getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
        if(sp.contains("notelist"))
        {
            s = new HashSet<String>();
            s = sp.getStringSet("notelist",null);
            if(s != null)
                noteList.addAll(s);

        }
        Log.d("Mainactivity","Main activity on create ");
        if(counter == 0) {
            noteList.add("My test item");
            noteList.add("My second item");
            noteList.add("My third item");
            counter++;
        }
        if(Main2Activity.notedited)
        {
            Calendar c = Calendar.getInstance();
            String time = "\n"+c.getTime();
            noteList.add(time);
            if(s != null)
            {
                s.clear();
                s.addAll(noteList);
                sp.edit().putStringSet("notelist",s).apply();
            }
            else {
                s = new HashSet<String>();
                s.addAll(noteList);
                sp.edit().putStringSet("notelist",s).apply();

            }
        }

        lv = (ListView) findViewById(R.id.mylist);


         arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                noteList);

        lv.setAdapter(arrayAdapter);
//        final SwipeDetector swipeDetector = new SwipeDetector(this);
//        lv.setOnTouchListener(swipeDetector);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              final int p = position;
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                noteList.remove(p);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                i.putExtra("id",position);
                startActivity(i);

            }
        });
    }
    public Context getcontext()
    {
        return getApplicationContext();

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

    public void aditem(View view)
    {

        Intent adnew = new Intent(getApplicationContext(),Main2Activity.class);
        adnew.putExtra("id",noteList.size()-1);
        startActivity(adnew);
//        if(s == null)
//        {
//            s = new HashSet<String>();
//
//        }
//
//        else
//        {
//            s.clear();
//        }
//        noteList.add("My other item");
//
//        s.addAll(noteList);
//        sp.edit().putStringSet("notelist",s).apply();
//
//arrayAdapter.notifyDataSetChanged();
    }
    public static class SwipeDetector implements View.OnTouchListener {

        public static enum Action {
            LR, // Left to Right
            RL, // Right to Left
            TB, // Top to bottom
            BT, // Bottom to Top
            None // when no action was detected
        }

        private static final String logTag = "SwipeDetector";
        private static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;
        private Action mSwipeDetected = Action.None;
        Context contx;

        public SwipeDetector(Context ctx)
        {

            contx = ctx;
        }
public boolean itemclicked(boolean set)
{
    return set;

}
        public boolean swipeDetected() {
            return mSwipeDetected != Action.None;
        }

        public Action getAction() {
            return mSwipeDetected;
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    mSwipeDetected = Action.None;
                    return false; // allow other events like Click to be processed
                }
                case MotionEvent.ACTION_MOVE: {
                    upX = event.getX();
                    upY = event.getY();

                    float deltaX = downX - upX;
                    float deltaY = downY - upY;

                    // horizontal swipe detection
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        if (deltaX < 0) {
//                            Log.d(Log.INFO,logTag, "Swipe Left to Right");
                            Toast.makeText(contx,"Swipe Left to Right",Toast.LENGTH_LONG).show();

                            mSwipeDetected = Action.LR;
                            return true;
                        }
                        if (deltaX > 0) {
//                            Logger.show(Log.INFO,logTag, "Swipe Right to Left");
                            Toast.makeText(contx,"Swipe Right to Left",Toast.LENGTH_LONG).show();
                            mSwipeDetected = Action.RL;
                            return true;
                        }
                    } else

                        // vertical swipe detection
                        if (Math.abs(deltaY) > MIN_DISTANCE) {
                            // top or down
                            if (deltaY < 0) {
                              //  Logger.show(Log.INFO,logTag, "Swipe Top to Bottom");
                                mSwipeDetected = Action.TB;
                                return false;
                            }
                            if (deltaY > 0) {
                               // Logger.show(Log.INFO,logTag, "Swipe Bottom to Top");
                                mSwipeDetected = Action.BT;
                                return false;
                            }
                        }
                    return true;
                }
            }
            return false;
        }
    }

}
