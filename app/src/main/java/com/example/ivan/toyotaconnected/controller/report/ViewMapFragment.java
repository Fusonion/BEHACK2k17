package com.example.ivan.toyotaconnected.controller.report;

/**
 * Created by Ivan on 10/8/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.toyotaconnected.R;
import com.example.ivan.toyotaconnected.controller.HomeActivity;
import com.example.ivan.toyotaconnected.dao.ContentProvider;
import com.example.ivan.toyotaconnected.dao.ContentProviderFactory;

import java.util.List;

/**
 * Fragment for displaying Not Shit
 *
 * @author tejun, chase
 */

public class ViewMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }
}
