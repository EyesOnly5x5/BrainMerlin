package de.eyesonly5x5.brainmerlin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Globals  extends ListActivity {
    @SuppressLint("StaticFieldLeak")
    private static final Globals instance = new Globals();
    private static MyDisplay metrics;

    // Beispiele für Daten...
    private byte[][] Tast = new byte[100][9];
    private int maxFelder = 0;
    private final boolean[] Flg = new boolean[100];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int Buty2 = 90;
    private int istGedrueckt = 0;
    private String woMischen = "Zauber";
    private boolean geMischt = false;
    List<RelativeLayout>viewButIDs = new ArrayList<>();

    // private Globals() { }

    public static Globals getInstance() {
        return instance;
    }

    public static void setMetrics( Resources hier ){
        metrics = new MyDisplay( hier );
    }
    public static MyDisplay getMetrics( ){
        return( metrics );
    }

    public byte[][] getTast() {
        return Tast;
    }

    public int decZuege( int Anzahl ) {
        Zuege -= Anzahl;
        return( Zuege );
    }
    public int incZuege() { return ++Zuege; }

    public TextView getAusgabe() {
        return Ausgabe;
    }
    public void setAusgabe(TextView wert) {
        Ausgabe = wert;
    }

    public SoundBib getSoundBib(boolean s) {
        return( (s)?SoundW:SoundF );
    }
    public void setSoundBib(boolean s, SoundBib wert) {
        if( s ) SoundW = wert;
        else SoundF = wert;
    }

    public boolean getGewonnen() {
        return gewonnen;
    }
    public void setGewonnen( boolean wert) {
        gewonnen = wert;
    }

    public int getActivity(){
        return Activity;
    }
    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( MainActivity c) { myContext = c; }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }

    public List<Integer> getColor(){ return( Color ); }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        geMischt = true;
        for (id = 0; id < maxFelder; id++) {
            Button button = buttons.get(id);
            Flg[id] = true;
            button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkGreen));
            button.setTextColor(button.getContext().getResources().getColor(R.color.white));
        }
        for (int i = 0; i < 25; i++) {
            id = r.nextInt(maxFelder);
            for (int idS : Tast[id]) if (idS > 0) changeFlg(idS - 1);
        }
    }

    @SuppressLint("WrongConstant")
    private int anzahlButtons(){
        int AnzBut = (((metrics.getMaxPixels()) / (int)(this.Buty*metrics.getFaktor()))-3);
        // int dimenX = (int) metrics.getMinPixels() / (column+1);
        if( AnzBut > 11 ) AnzBut = 11;
        AnzBut *= Anzahl;
        return( AnzBut );
    }

    public int getButy2(){ return( Buty2 ); }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        /* if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myContext.getResources().getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {*/
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
        // }
        BUTTON_IDS = ret;
        maxFelder = BUTTON_IDS.length;
        return (BUTTON_IDS);
    }

    @SuppressLint("NonConstantResourceId")
    private int getWerWoWas(){
        int ret = -1;
        switch( Activity ){
            case R.layout.activity_merlin:
                ret = 0;
                break;
            case R.layout.activity_gandalf:
                ret = 1;
                break;
            case R.layout.activity_harry:
                ret = 2;
                break;
            case R.layout.activity_houdini:
                ret = 4;
                break;
        }
        return( ret );
    }

    public void setGameData(String[] tast) {
        maxFelder = tast.length;
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        istGedrueckt = 0;

        if( maxFelder > 1 ){
            for ( byte feld = 0; feld < 100; feld++ )
                for( byte x = 0; x<9; x++ ) this.Tast[feld][x] = 0;
            int i = 0;
            for (String x : tast) {
                String[] tmp = x.split(",");
                int j = 0;
                for (String y : tmp) {
                    this.Tast[i][j] = Byte.parseByte(y.trim());
                    j++;
                }
                i++;
            }
            i = 0;
            for (String x : tast) {
                Flg[i] = true;
                i++;
            }
        } else {
            maxFelder = 100;
            for ( byte feld = 0; feld < 100; feld++ )
                for( byte x = 0; x<9; x++ ) this.Tast[feld][x] = beRechneFeld((byte) (feld + 1), x );
            for ( int i = 0; i < 100; i++ ) Flg[i] = true;
        }
    }

    private byte beRechneFeld( byte id, byte i ){
        byte[] arr = {(byte) (id-9), (byte) (id-10), (byte) (id-11), (byte) (id-1), id, (byte) (id+1), (byte) (id+9), (byte) (id+10), (byte) (id+11)};
        if( (id%10)==1 ) { arr[2] = 0; arr[3] = 0; arr[6] = 0; }
        else if( (id%10)==0 ) { arr[0] = 0; arr[5] = 0; arr[8] = 0; }

        if( arr[i] < 0 ) arr[i] = 0;
        if( arr[i] > maxFelder ) arr[i] = 0;
        return( arr[i] );
    }

    @SuppressLint("ResourceAsColor")
    public void changeFlg(int id) {
        Button button = buttons.get(id);
        if (Flg[id]) {
            button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkRed));
            button.setTextColor(button.getContext().getResources().getColor(R.color.Gelb));
        } else {
            button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkGreen));
            button.setTextColor(button.getContext().getResources().getColor(R.color.white));
        }
        Flg[id] = !Flg[id];
    }

    public boolean checkFlg() {
        boolean Flage = true;
        for (int i = 0; i < maxFelder && Flage; i++) if (!Flg[i]) Flage = false;
        return (Flage);
    }

    static class SoundBib extends AppCompatActivity {
        private SoundPool soundPool;
        List<Integer> sound = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main);
        }

        public SoundBib(boolean s, Context context) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

            if( s ) {
                sound.add(soundPool.load(context, R.raw.won1, 1));
                sound.add(soundPool.load(context, R.raw.won2, 1));
                sound.add(soundPool.load(context, R.raw.won3, 1));
                sound.add(soundPool.load(context, R.raw.won4, 1));
                sound.add(soundPool.load(context, R.raw.won5, 1));
            } else {
                sound.add(soundPool.load(context, R.raw.fail1, 1));
                sound.add(soundPool.load(context, R.raw.fail2, 1));
                sound.add(soundPool.load(context, R.raw.fail3, 1));
                sound.add(soundPool.load(context, R.raw.fail4, 1));
            }
        }

        // When users click on the button "Gun"
        public void playSound() {
            soundPool.autoPause();
            Random r = new Random();
            int id = r.nextInt(sound.size());
            soundPool.play(sound.get(id), 0.25F, 0.25F, 0, 0, 1);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            soundPool.release();
            soundPool = null;
        }
    }
    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        oView.setText( Wat );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view -> customDialog.dismiss());
        customDialog.setCancelable(false);
        customDialog.show();
    }
}