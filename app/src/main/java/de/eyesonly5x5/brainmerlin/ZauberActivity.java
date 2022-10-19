package de.eyesonly5x5.brainmerlin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ZauberActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    byte[][] Tast = daten.getTast();
    int[] BUTTON_IDS;
    int anzahlButtZeile;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int[] oldID = {-1};
        int margin = 2;
        int buttonSize;
        super.onCreate(savedInstanceState);
        setContentView(daten.getActivity());
        // daten.setMyContext( this );
        BUTTON_IDS = daten.getButtonIDs();
        anzahlButtZeile = (int)Math.sqrt(BUTTON_IDS.length);
        TextView AusGtmp = findViewById(R.id.Kopf);
        AusGtmp.setText( daten.getWoMischen() );
        AusGtmp.setTextSize( daten.getMetrics().pxToDp((int)(AusGtmp.getTextSize()*daten.getMetrics().getFaktor())) );
        buttonSize = daten.getMetrics().getButtonSize( (int)Math.sqrt( BUTTON_IDS.length ), margin ) * 13 / 16;

        for(int id : BUTTON_IDS) {
            Button button;
            button = addbtn( id, margin );
            button.setOnClickListener(view -> {
                if( !daten.getGewonnen()) {
                    TextView AusG = daten.getAusgabe();
                    int id1 = Integer.parseInt(button.getTag().toString()) - 1;
                    if( id1 == oldID[0]){
                        daten.decZuege(2);
                        oldID[0] = -1;
                    } else {
                        oldID[0] = id1;
                    }
                    AusG.setText(getString(R.string.Zuge) + daten.incZuege());
                    for (int idS : Tast[id1]) if (idS != 0) daten.changeFlg(idS - 1);
                    if (daten.checkFlg()) {
                        AusG.setText(AusG.getText() + " --> Bravo");
                        daten.setGewonnen(true);
                        daten.getSoundBib(true).playSound();
                    }
                }
            });
            RelativeLayout.LayoutParams lP = (RelativeLayout.LayoutParams) button.getLayoutParams();
            lP.setMargins( margin, margin, margin, margin);
            lP.width = buttonSize;
            lP.height = buttonSize;
            daten.addButton(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return( true );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mischy:
                daten.Mischer();
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitZauber );
                return( true );
        }
        return( super.onOptionsItemSelected( item) );
    }

    @SuppressLint("ResourceAsColor")
    private Button addbtn( int id, int margin ) {
        RelativeLayout rLbutty = (RelativeLayout) findViewById(R.id.butty);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(daten.getButy2(), daten.getButy2());
        layoutParams.setMargins(margin, margin, margin, margin);

        Button button = new Button(this);
        Log.d("Debuggy:", "ButtID:"+id);
        if( id == 1 ) {
            // nixs
        } else if ( id < (anzahlButtZeile+1) ){
            layoutParams.addRule(RelativeLayout.END_OF, (id-1) );
            layoutParams.addRule(RelativeLayout.RIGHT_OF, (id-1) );
        } else if ( id % anzahlButtZeile == 1 ){
            layoutParams.addRule(RelativeLayout.BELOW, (id-anzahlButtZeile ) );
        } else {
            layoutParams.addRule(RelativeLayout.END_OF, (id-1) );
            layoutParams.addRule(RelativeLayout.RIGHT_OF, (id-1) );
            layoutParams.addRule(RelativeLayout.BELOW, (id-anzahlButtZeile ) );
        }
        button.setTag("" + id);
        button.setId( id );

        button.setBackgroundColor( button.getContext().getResources().getColor(R.color.DarkGreen) );

        rLbutty.addView(button, layoutParams);
        return( button );
    }
}