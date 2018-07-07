package com.example.hp.brahmsamaj;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.hp.brahmsamaj.utility.UserPreferenceManager;

public class BaseActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public FrameLayout frameLayout;
    public UserPreferenceManager userPreferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.container);
        userPreferenceManager = new UserPreferenceManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        showToolbar(true);
    }

    public void showToolbar(boolean showtoolbar) {

        if (showtoolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            getSupportActionBar().hide();
        }
    }

    public void FragmentTransitionReplace(Fragment fragment, String title, Bundle bundle) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_body, fragment);
            getSupportFragmentManager().popBackStack();
            getFragmentManager().popBackStackImmediate();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    public void removeAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void FragmentTransitionAdd(Fragment fragment, String title, Bundle bundle) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
