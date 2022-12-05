package io.github.eyesonly5x5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.eyesonly5x5.brainmerlin.R;

public class MainActivity extends AppCompatActivity {

    Globals daten = Globals.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daten.setMyContext( this );
        daten.setMetrics(getResources());

        TextView AusG = findViewById(R.id.Kopf);
        AusG.setText(getString(R.string.title1));
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        daten.setSoundBib(true,new Globals.SoundBib( true,this));
        daten.setSoundBib(false,new Globals.SoundBib( false,this));

        Button Merlin = findViewById(R.id.Merlin);
        Merlin.setTextSize( daten.getMetrics().pxToDp((int)(Merlin.getTextSize()*daten.getMetrics().getFaktor())) );
        Button Gandalf = findViewById(R.id.Gandalf);
        Gandalf.setTextSize( daten.getMetrics().pxToDp((int)(Gandalf.getTextSize()*daten.getMetrics().getFaktor())) );
        Button Harry = findViewById(R.id.Harry);
        Harry.setTextSize( daten.getMetrics().pxToDp((int)(Harry.getTextSize()*daten.getMetrics().getFaktor())) );
        Button Houdini = findViewById(R.id.Houdini);
        Houdini.setTextSize( daten.getMetrics().pxToDp((int)(Houdini.getTextSize()*daten.getMetrics().getFaktor())) );

        Merlin.setOnClickListener(view -> {
            Merlin.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_zauber);
            daten.setWoMischen( "Merlin" );
            daten.setGameData(getResources().getStringArray(R.array.Merlin));
            startActivity(new Intent(getApplicationContext(),ZauberActivity.class));
            Merlin.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        Gandalf.setOnClickListener(view -> {
            Gandalf.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_zauber);
            daten.setWoMischen( "Gandalf" );
            daten.setGameData(getResources().getStringArray(R.array.Gandalf));
            startActivity(new Intent(getApplicationContext(),ZauberActivity.class));
            Gandalf.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        Harry.setOnClickListener(view -> {
            Harry.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_zauber);
            daten.setWoMischen( "Harry" );
            daten.setGameData(getResources().getStringArray(R.array.Harry));
            startActivity(new Intent(getApplicationContext(),ZauberActivity.class));
            Harry.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        Houdini.setOnClickListener(view -> {
            Houdini.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_zauber);
            daten.setWoMischen( "Houdini" );
            daten.setGameData(getResources().getStringArray(R.array.Houdini));
            startActivity(new Intent(getApplicationContext(),ZauberActivity.class));
            Houdini.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
    }
}