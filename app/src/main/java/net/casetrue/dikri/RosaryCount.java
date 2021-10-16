package net.casetrue.dikri;
/*
 * Created by MOHAMED EL YONOUSI on 12/11/2020.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class RosaryCount extends AppCompatActivity
{
    /**
     * Global variable declaration
     **/
    private static final String DIKRI_SHARED_PREFERENCE = "dikriSharedPreferences";
    private static final String DIKRI_TEXT = "dikriText";

    ConstraintLayout rootView;
    TextView txtCount;
    int stockCount;
    int countValue;

    AdView adBannerBottomGoogleHuawei;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dikr_rosary);

            /*
             * find view by id
             */
            rootView = findViewById(R.id.root_view);
            txtCount = findViewById(R.id.txt_count_rosary);

            /*
             * load data save from sharedPref
             */
            loadData();

            /*
             * set on click listener on constraint layout
             */
            rootView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    /*
                     * add +1 on one click
                     */
                    stockCount++;

                    /*
                     * get TextView value
                     */
                    countValue = Integer.parseInt(txtCount.getText().toString());

                    /*
                     * increment TextView +1
                     */
                    countValue++;

                    /*
                     * set a value increment to TextView
                     */
                    txtCount.setText(String.valueOf(countValue));
                }
            });
        } catch (Exception exception)
        {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*
         * this is a google ads
         */
        try
        {
            MobileAds.initialize(this, new OnInitializationCompleteListener()
            {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus)
                {
                    adBannerBottomGoogleHuawei = findViewById(R.id.adBannerBottomGoogleHuawei);
                    adRequest = new AdRequest.Builder().build();
                    adBannerBottomGoogleHuawei.loadAd(adRequest);
                }
            });
        } catch (Exception m)
        {
            Toast.makeText(this, "Error: load GOOGLE ADS", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * save data clcik sharedPref
     */
    public void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(DIKRI_SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DIKRI_TEXT, stockCount);
        editor.apply();
    }

    /**
     * load data from sharedPref
     */
    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(DIKRI_SHARED_PREFERENCE, MODE_PRIVATE);
        stockCount = sharedPreferences.getInt(DIKRI_TEXT, 0);
    }

    /**
     * save value changed click when back pressed layout on extern file, using class method
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        /*
         * save data on shared
         */
        saveData();
    }

    /**
     * save value changed click when stop layout on extern file, using class method
     */
    @Override
    protected void onStop()
    {
        super.onStop();

        /*
         * save data on shared
         */
        saveData();
    }
}