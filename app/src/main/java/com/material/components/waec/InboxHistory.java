package com.material.components.waec;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.adapter.AdapterListBasic;
import com.material.components.data.DataGenerator;
import com.material.components.model.People;
import com.material.components.utils.Tools;

import java.util.List;

public class InboxHistory extends AppCompatActivity {

    private View parent_view;
    private ActionBar actionBar;
    private Toolbar toolbar;

    private Menu menu_navigation;
    private DrawerLayout drawer;
    private View navigation_header;


    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_history);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initNavigationMenu();
        initComponent();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Cards Inbox");
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<People> items = DataGenerator.getPeopleData(this);
        items.addAll(DataGenerator.getPeopleData(this));
        items.addAll(DataGenerator.getPeopleData(this));

        //set data and list adapter
        mAdapter = new AdapterListBasic(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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

                        Intent goto_home = new Intent(InboxHistory.this,InboxHistory.class);
                        startActivity(goto_home);
                        // add navigation drawer item onclick method here
                        //Toast.makeText(getApplicationContext(), item.getTitle() + " Booo", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_buy_waec_card:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_buy_waec_card = new Intent(InboxHistory.this,BuyWaecCard.class);
                        startActivity(goto_buy_waec_card);
                        break;
                    case R.id.nav_placement_card:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_buy_placement_card = new Intent(InboxHistory.this,BuyPlacementCard.class);
                        startActivity(goto_buy_placement_card);
                        break;
                    case R.id.nav_check_waec_result:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_check_waec_results = new Intent(InboxHistory.this,CheckWaecResults.class);
                        startActivity(goto_check_waec_results);
                        break;
                    case R.id.nav_placement_result:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_check_placement_results = new Intent(InboxHistory.this,CheckPlacementResults.class);
                        startActivity(goto_check_placement_results);
                        break;
                    case R.id.nav_cards_history:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent goto_inbox_history = new Intent(InboxHistory.this,InboxHistory.class);
                        startActivity(goto_inbox_history);
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
}