package com.material.components.waec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.utils.Tools;

public class DashboardGridFab extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;

    private Menu menu_navigation;
    private DrawerLayout drawer;
    private View navigation_header;
    public String emailStored="", phoneStored="", fullnameStored="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_grid_fab);

        initToolbar();
        initNavigationMenu();

        SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        emailStored = pref.getString("email", null);
        phoneStored = pref.getString("phonenumber", null);
        fullnameStored = pref.getString("fullname", null);

        if(fullnameStored == null){

            final TextView welcomUser = (TextView) findViewById(R.id.userName);
            welcomUser.setText("Hi, " + phoneStored);

        } else {

            final TextView welcomUser = (TextView) findViewById(R.id.userName);
            welcomUser.setText("Hi," + fullnameStored);
        }



        findViewById(R.id.frame_buyCard).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent buyWaecCard = new Intent(DashboardGridFab.this,BuyWaecCard.class);
                startActivity(buyWaecCard);
            }
        });

        findViewById(R.id.buy_placement_card).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent buyPlacementCard = new Intent(DashboardGridFab.this,BuyPlacementCard.class);
                startActivity(buyPlacementCard);
            }
        });

        findViewById(R.id.check_waec_results).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent checkWaecResults = new Intent(DashboardGridFab.this,CheckWaecResults.class);
                startActivity(checkWaecResults);
            }
        });

        findViewById(R.id.check_placement_results).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent checkPlacementresult = new Intent(DashboardGridFab.this,CheckPlacementResults.class);
                startActivity(checkPlacementresult);
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");
        Tools.setSystemBarColor(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNavigationMenu() {
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                menu_navigation = nav_view.getMenu();
            }
        });

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        //Change Navigation Icon
                        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

                       Intent goto_home = new Intent(DashboardGridFab.this,DashboardGridFab.class);
                       startActivity(goto_home);
                        // add navigation drawer item onclick method here
                        //Toast.makeText(getApplicationContext(), item.getTitle() + " Booo", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_buy_waec_card:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_buy_waec_card = new Intent(DashboardGridFab.this,BuyWaecCard.class);
                        startActivity(goto_buy_waec_card);
                        break;
                    case R.id.nav_placement_card:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_buy_placement_card = new Intent(DashboardGridFab.this,BuyPlacementCard.class);
                        startActivity(goto_buy_placement_card);
                        break;
                    case R.id.nav_check_waec_result:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_check_waec_results = new Intent(DashboardGridFab.this,CheckWaecResults.class);
                        startActivity(goto_check_waec_results);
                        break;
                    case R.id.nav_placement_result:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_check_placement_results = new Intent(DashboardGridFab.this,CheckPlacementResults.class);
                        startActivity(goto_check_placement_results);
                        break;
                    case R.id.nav_cards_history:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_inbox_history = new Intent(DashboardGridFab.this,InboxHistory.class);
                        startActivity(goto_inbox_history);
                        break;
                    case R.id.nav_setting:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_profile = new Intent(DashboardGridFab.this,FormProfileData.class);
                        startActivity(goto_profile);
                        break;
                    case R.id.nav_help:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_help = new Intent(DashboardGridFab.this,Help.class);
                        startActivity(goto_help);
                        break;
                }
                //Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                drawer.closeDrawers();
                return true;
            }
        });

        // navigation header
        navigation_header = nav_view.getHeaderView(0);
        //nav_view.inflateMenu(R.menu.menu_navigation_drawer_mail);
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
