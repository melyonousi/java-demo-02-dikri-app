package net.casetrue.dikri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

public class DikrActivity extends AppCompatActivity {

    /**
     * this is a global declaration variables
     */
    private static final String DIKRI_SHARED_PREFERENCE = "dikriSharedPreferences";
    private static final String DIKRI_TEXT = "dikriText";

    int stockCount;
    TextView txtCount;

    Loading loading;

    /**
     * this is onCreate layout dikr main
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dikr_main);

            /*
             * get TextViewCount id
             */
            txtCount = findViewById(R.id.dikrCount);

            /*
             * load data save from sharedPref
             */
            loadData();

            loading = new Loading(DikrActivity.this);

        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * load data from sharedPref
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(DIKRI_SHARED_PREFERENCE, MODE_PRIVATE);
        stockCount = sharedPreferences.getInt(DIKRI_TEXT, 0);

        txtCount.setText(MessageFormat.format("عدد الأذكار المقرؤة:\n {0}", stockCount));
    }

    /**
     * this is onStart layout
     */
    @Override
    protected void onStart() {
        try {
            super.onStart();

            /*
             * load data save from sharedPref
             */
            loadData();
        } catch (Exception m) {
            Toast.makeText(this, m.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loading.dismissLoading();
    }

    /**
     * this method send click main button layout
     *
     * @param view
     */
    public void dikrView(View view) {
        loading.startLoading();
        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loading.dismissLoading();
//            }
//        }, 5000);
        Button btn = (Button) view;
        Intent intent = new Intent(getBaseContext(), dikrRead.class);
        intent.putExtra("EXTRA_SESSION_ID", String.valueOf(btn.getTag()));
        startActivity(intent);
    }

    /**
     * this method send click button rosary intent
     *
     * @param view
     */
    public void rosaryView(View view) {
        loading.startLoading();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loading.dismissLoading();
//            }
//        }, 5000);
        Intent intent = new Intent(getBaseContext(), RosaryCount.class);
        startActivity(intent);
    }
}