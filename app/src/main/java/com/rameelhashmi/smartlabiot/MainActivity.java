package com.rameelhashmi.smartlabiot;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//This is the main activity class use for further call for other activities, shared preference is used to store the current state of application
//In case user ants to use other application and wanted to continue from this application
public class MainActivity extends FragmentActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getPreferences(0);

        Fragment fragment;
        if (pref.getBoolean(consts.logged_in, false)) {
            fragment = new user_profile();
        } else

        {
            fragment = new user_login();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_box, fragment);
        ft.commit();
    }

}











