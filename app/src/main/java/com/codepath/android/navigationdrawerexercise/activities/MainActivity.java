package com.codepath.android.navigationdrawerexercise.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codepath.android.navigationdrawerexercise.R;
import com.codepath.android.navigationdrawerexercise.fragments.FamilyGuyFragment;
import com.codepath.android.navigationdrawerexercise.fragments.FuturamaFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SimpsonsFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SouthParkFragment;


public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawer;
    ActionBarDrawerToggle drawerToggler;
    Toolbar toolbar;
    NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggler = setupDrawerToggle();

        mDrawer.addDrawerListener(drawerToggler);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        nvDrawer.getMenu().getItem(0).setChecked(true);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, new SouthParkFragment()).commit();
        setTitle(R.string.south_park);
    }

    private void setupDrawerContent(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(
                new OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.south_park:
                fragmentClass = SouthParkFragment.class;
                break;
            case R.id.simpsons:
                fragmentClass = SimpsonsFragment.class;
                break;
            case R.id.futurama:
                fragmentClass = FuturamaFragment.class;
                break;
            case R.id.family_guy:
                fragmentClass = FamilyGuyFragment.class;
                break;
            default:
                fragmentClass = SouthParkFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, fragment).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggler.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggler.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggler.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


}
