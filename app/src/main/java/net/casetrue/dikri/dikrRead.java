package net.casetrue.dikri;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class dikrRead extends AppCompatActivity
{
    /**
     * this is a global declaration variables
     */
    private static final String DIKRI_SHARED_PREFERENCE = "dikriSharedPreferences";
    private static final String DIKRI_TEXT = "dikriText";

    ArrayList<Dikr> dikrList;
    int stockCount;
    int change;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    DikrAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    AdView adBannerBottomGoogle;
    AdRequest adRequest;

    /**
     * this is onCreate read layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dikr_read);

        try
        {
            /*
             * this is initialise variables on onCreate
             */
            recyclerView = findViewById(R.id.recycle_view);
            progressBar = findViewById(R.id.progress_bar);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            dikrList = new ArrayList<>();
            change = 0;


            /*
             * load data from sharedPref
             */
            loadData();

            switch (getIntent().getStringExtra("EXTRA_SESSION_ID"))
            {
                case "wakeup":
                    dikrList = Dikr.open(getResources().openRawResource(R.raw.wake_up));
                    break;
                case "morning":
                    dikrList = Dikr.open(getResources().openRawResource(R.raw.morning));
                    break;
                case "evening":
                    dikrList = Dikr.open(getResources().openRawResource(R.raw.evening));
                    break;
                case "sleep":
                    dikrList = Dikr.open(getResources().openRawResource(R.raw.sleep));
                    break;
                case "salaat":
                    dikrList = Dikr.open(getResources().openRawResource(R.raw.salaat));
                    break;
            }

            /*
             * get onClickListener and set text adapter value
             */
            setClick();

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
                    adBannerBottomGoogle = findViewById(R.id.adBannerBottomGoogle);
                    adRequest = new AdRequest.Builder().build();
                    adBannerBottomGoogle.loadAd(adRequest);
                }
            });
        } catch (
                Exception m)

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
     * set on click listener method and get button text value
     */
    public void setClick()
    {
        /*
         * initialise adapter by class using
         */
        adapter = new DikrAdapter(dikrList);

        /*
         * set adapter to recycler view
         */
        recyclerView.setAdapter(adapter);

        /*
         * set onClickListener for adapter
         */
        adapter.setDikrClickListener(new DikrAdapter.OnDikrClickListener()
        {
            /**
             * this interface empliments
             * @param position
             */
            @Override
            public void OnDikrClick(int position)
            {
                /*
                 * get value from cardView
                 */
                if (adapter != null)
                    setBtnCount(position, adapter.dikrs.get(position).getNbCount());
            }
        });
    }

    /**
     * set button text value
     *
     * @param position
     * @param btnCount
     */
    public void setBtnCount(int position, String btnCount)
    {
        /*
         * set count value from onClickListener position click
         */
        int count = Integer.parseInt(btnCount);

        /*
         * test for remove item
         */
        if (count == 1)
        {
            /*
             * set click value +1
             */
            stockCount++;

            /*
             * save data count click in sharedPref
             */
            saveData();

            /*
             * set progress vale click +1
             */
            progressBar.setProgress(++change);
            removeDikr(position);

            /*
             * close layout when emtoy adapter
             */
            if (adapter.dikrs.isEmpty())
                dikrRead.this.finish();
            return;
        }

        /*
         * set click value +1
         */
        stockCount++;

        /*
         * save data count click in sharedPref
         */
        saveData();

        /*
         * mines click on recycler click listener
         */
        count--;

        /*
         * change value set onClickListener by using class method
         */
        dikrList.get(position).changeBtnCount(String.valueOf(count));

        /*
         * notify recyclerView changed on item click
         */
        adapter.notifyItemChanged(position);
    }

    /**
     * this method remove an item from recyclerView by onCLickListener method
     *
     * @param position
     */
    public void removeDikr(int position)
    {
        /*
         * remove item from recyclerView position by onClickListener
         */
        dikrList.remove(position);

        /*
         * notify recyclerView as removed an item by onClickListener adapter position
         */
        adapter.notifyItemRemoved(position);
    }

    /**
     * this is onStart read dikr layout to set progressBar value
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        try
        {
            if (dikrList.size() <= 0)
            {
                progressBar.setMax(2);
                progressBar.setProgress(1);
            }
            else
            {
                progressBar.setMax(dikrList.size());
                progressBar.setProgress(change);
            }
        } catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}