package com.example.hp.brahmsamaj.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hp.brahmsamaj.BaseActivity;
import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.fragment.FragmentDrawer;
import com.example.hp.brahmsamaj.fragment.FragmentMyProfile;
import com.example.hp.brahmsamaj.interfaces.DrawerItemClickListener;
import com.example.hp.brahmsamaj.retrofit.DataProviderResponse;
import com.example.hp.brahmsamaj.retrofit.NetworkDataProvider;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;
import com.example.hp.brahmsamaj.utility.Utility;
import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;

import static com.example.hp.brahmsamaj.Brahmsamaj.getContext;

/**
 * Created by HP on 07-06-2018.
 */

public class HomeActivity extends BaseActivity implements DrawerItemClickListener {

    Toolbar toolbarHome;
    FrameLayout containerBody;
    DrawerLayout drawerLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private FragmentDrawer fragmentDrawer;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_home, frameLayout);
        ButterKnife.bind(this, view);

        toolbarHome = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.drawer_layout);

        showToolbar(false);
        setSupportActionBar(toolbarHome);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentDrawer = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbarHome);
        fragmentDrawer.setDrawerListener(this);
        setupDrawerToggle();
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbarHome, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(int id) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (id) {
            case R.id.ll_drawer_my_profile:
                fragment = new FragmentMyProfile();
                title = getString(R.string.drawer_my_profile);
                break;

            case R.id.ll_drawer_update_avtar:
                fragment = new FragmentMyProfile();
                title = getString(R.string.drawer_update_avtar);
                break;

            case R.id.ll_drawer_logout:
                logout(false);
                UserPreferenceManager.clearPreference();
                Utility.redirectToActivity(HomeActivity.this, LoginActivityNew.class, true, null, null, 0, null);
                break;
        }
        FragmentTransitionReplace(fragment, title, null);
        drawerLayout.closeDrawers();
    }

    private void logout(boolean isLoaderRequired) {
        NetworkDataProvider networkDataProvider = new NetworkDataProvider(getContext());
        networkDataProvider.setResponseListener(new DataProviderResponse() {

            Gson gson = new GsonBuilder().create();

            @Override
            public void onDataProviderResult(ServiceResponse response) {
                if (response.getStatus_code() == 200) {
                    Log.e("logout", response.getMessage());
                    Toast.makeText(HomeActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataProviderResult(ErrorsResponse errorsResponse) {

            }
        });
        networkDataProvider.makeHttpCall(null, AppConstants.ApiConstant.LOGOUT, isLoaderRequired);
    }
}
