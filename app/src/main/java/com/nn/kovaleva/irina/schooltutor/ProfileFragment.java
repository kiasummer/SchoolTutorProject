package com.nn.kovaleva.irina.schooltutor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.FloatingActionButton;


public class ProfileFragment extends Fragment {
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (Actor.getsInstance().ifTutor) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_profile_student, container, false);
        }

        FloatingActionButton floatingActionButton = new FloatingActionButton.Builder(null, (Fragment)this)
                .withDrawable(getResources().getDrawable(R.drawable.plus))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0,0,16,16)
                .create();

        return view;
    }
}
