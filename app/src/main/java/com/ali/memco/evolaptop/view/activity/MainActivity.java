package com.ali.memco.evolaptop.view.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.SignUp;
import com.ali.memco.evolaptop.adapter.AppFeatureAdapter;
import com.ali.memco.evolaptop.dataModel.DataFakeGenrator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FloatingActionButton  floatButton;
    private AppFeatureAdapter appFeatureAdapter;
   public static Typeface bYkan;
   public static Typeface iranSansBold;
   public static Typeface iranSans;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       bYkan = Typeface.createFromAsset(getAssets(),"fonts/bykan.ttf");
       iranSansBold = Typeface.createFromAsset(getAssets(),"fonts/iransansb.ttf");
       iranSans = Typeface.createFromAsset(getAssets(),"fonts/iransans.ttf");

        setupRecyclerView();
        setupActionBar();
        setupNavigationView();
        setupFAB();



    }


    private void setupFAB() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatButton.getVisibility() == View.VISIBLE){
                    floatButton.hide();
                }else if (dy < 0 && floatButton.getVisibility() != View.VISIBLE){
                    floatButton.show();
                }
            }
        });
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_home:
                        Toast.makeText(MainActivity.this, "صفحه اصلی کلیک شد", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_testApp:
                       startActivity(new Intent(MainActivity.this, SignUp.class));
                        break;

                    case R.id.menu_setting:
                        Toast.makeText(MainActivity.this, "تنظیمات کلیک شد", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void setupActionBar() {

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,0,0 );

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        floatButton = findViewById(R.id.main_float_actionbar);
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.main_coordinator);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout,"پیام متنی برای نمایش به کاربر",Snackbar.LENGTH_LONG).
                        setAction("انجام شد", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "به درستی وارد شد", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });



    }

    private void setupRecyclerView() {

        recyclerView = findViewById(R.id.main_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0){
                    return 2;
                }else {
                   return 1;
                }

            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        appFeatureAdapter = new AppFeatureAdapter(this);
        recyclerView.setAdapter(appFeatureAdapter);
        appFeatureAdapter.setupAppFeatureAdapter(DataFakeGenrator.getAppFeature(this));



    }
}
