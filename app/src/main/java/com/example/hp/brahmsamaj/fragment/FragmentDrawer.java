package com.example.hp.brahmsamaj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.interfaces.DrawerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentDrawer extends Fragment {


    @BindView(R.id.iv_drawer_my_profile)
    ImageView ivDrawerMyProfile;
    @BindView(R.id.txt_drawer_menu)
    TextView txtDrawerMenu;
    @BindView(R.id.ll_drawer_my_profile)
    LinearLayout llDrawerMyProfile;
    @BindView(R.id.iv_drawer_identity)
    ImageView ivDrawerIdentity;
    @BindView(R.id.txt_drawer_identity)
    TextView txtDrawerIdentity;
    @BindView(R.id.ll_drawer_identity)
    LinearLayout llDrawerIdentity;
    @BindView(R.id.iv_drawer_update_avtar)
    ImageView ivDrawerUpdateAvtar;
    @BindView(R.id.txt_drawer_update_avtar)
    TextView txtDrawerUpdateAvtar;
    @BindView(R.id.ll_drawer_update_avtar)
    LinearLayout llDrawerUpdateAvtar;
    @BindView(R.id.iv_drawer_update_password)
    ImageView ivDrawerUpdatePassword;
    @BindView(R.id.txt_drawer_update_password)
    TextView txtDrawerUpdatePassword;
    @BindView(R.id.ll_drawer_update_password)
    LinearLayout llDrawerUpdatePassword;
    @BindView(R.id.iv_drawer_logout)
    ImageView ivDrawerLogout;
    @BindView(R.id.txt_drawer_logout)
    TextView txtDrawerLogout;
    @BindView(R.id.ll_drawer_logout)
    LinearLayout llDrawerLogout;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;

    Unbinder unbinder;
    private DrawerItemClickListener drawerItemClickListener;

    public FragmentDrawer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflating view layout
        View view = inflater.inflate(R.layout.raw_drawer, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_drawer_my_profile, R.id.ll_drawer_identity, R.id.ll_drawer_update_avtar, R.id.ll_drawer_update_password, R.id.ll_drawer_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_drawer_my_profile:
                drawerItemClickListener.onItemClick(view.getId());
                break;
            case R.id.ll_drawer_identity:
                drawerItemClickListener.onItemClick(view.getId());
                break;
            case R.id.ll_drawer_update_avtar:
                drawerItemClickListener.onItemClick(view.getId());
                break;
            case R.id.ll_drawer_update_password:
                drawerItemClickListener.onItemClick(view.getId());
                break;
            case R.id.ll_drawer_logout:
                drawerItemClickListener.onItemClick(view.getId());
                break;
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void setDrawerListener(DrawerItemClickListener listener) {
        this.drawerItemClickListener = listener;
    }
}
