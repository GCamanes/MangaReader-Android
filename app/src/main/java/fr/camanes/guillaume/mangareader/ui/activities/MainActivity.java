package fr.camanes.guillaume.mangareader.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fr.camanes.guillaume.mangareader.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Boolean connection_status;
    InternalNetworkChangeReceiver internalNetworkChangeReceiver;
    MenuItem item_connectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        connection_status = (netInfo != null && netInfo.isConnected());

        internalNetworkChangeReceiver = new InternalNetworkChangeReceiver();
        registerReceiver();

    }

    @Override
    protected void onDestroy()
    {
        try
        {
            // Make sure to unregister internal receiver in onDestroy().
            unregisterReceiver(internalNetworkChangeReceiver);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        this.item_connectivity = menu.findItem(R.id.item_connectivity);
        this.manageConnectivity();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.item_connectivity) {

        }

        return super.onOptionsItemSelected(item);
    }

    public void displaySettingsActivity() {
        Intent intentInfo = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intentInfo);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mangas_list) {

        } else if (id == R.id.nav_download_manga) {

        } else if (id == R.id.nav_download_chapter) {

        } else if (id == R.id.nav_settings) {
            displaySettingsActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void manageConnectivity() {
        if (item_connectivity != null) {
            if (connection_status) {
                item_connectivity.setIcon(R.drawable.ic_signal_cellular_4_bar_black_24dp);
            } else {
                item_connectivity.setIcon(R.drawable.ic_signal_cellular_connected_no_internet_4_bar_black_24dp);
            }
        }
    }

    private void registerReceiver() {
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(internalNetworkChangeReceiver, intentFilter);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    class InternalNetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            connection_status = (netInfo != null && netInfo.isConnected());
            Log.e("Network Availability ", ""+connection_status);

            MainActivity.this.manageConnectivity();
        }
    }
}
