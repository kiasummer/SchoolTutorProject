package com.nn.kovaleva.irina.schooltutor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.Education;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.UI.EditProfileActivity;
import com.nn.kovaleva.irina.schooltutor.UI.LoginActivity;
import com.nn.kovaleva.irina.schooltutor.core.Controller;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements View.OnClickListener,
        PopupMenu.OnMenuItemClickListener{
    public static final String TAG = "ProfileFragment";
    public static final int OWNER = 1;
    public static final int VISITERFRIEND = 2;
    public static final int VISITER = 3;
    public static int status;

    private FloatingActionButton editTuturBtn, plusUserBtn;//, editStudentBtn;
    private ImageView backBtn, menuBtn;
    private TextView title, firstName, secondName, yearOfEducation,
            phoneNumber, subjects, educations, address, price,
            phoneNumberTitle, subjectsTitle, educationsTitle,
            addressTitle, priceTitle, titleFragment;
    private View view;
    private PopupMenu popupMenu;
    public static User user = null;


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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        firstName = view.findViewById(R.id.first_name);
        secondName = view.findViewById(R.id.second_name);
        //patronymic = view.findViewById(R.id.patronymic);
        phoneNumber = view.findViewById(R.id.telNumber_profile);
        subjects = view.findViewById(R.id.subjects_profile);
        educations = view.findViewById(R.id.education_profile);
        price = view.findViewById(R.id.price_profile);
        address = view.findViewById(R.id.address_profile);
        yearOfEducation = view.findViewById(R.id.year_of_educ);

        phoneNumberTitle = view.findViewById(R.id.telNumber_profile_title);
        subjectsTitle = view.findViewById(R.id.subjects_profile_title);
        educationsTitle = view.findViewById(R.id.education_profile_title);
        priceTitle = view.findViewById(R.id.price_profile_title);
        addressTitle = view.findViewById(R.id.address_profile_title);
        title = view.findViewById(R.id.title_photo);
        titleFragment = view.findViewById(R.id.profile_title_fragment);

        editTuturBtn = view.findViewById(R.id.fab_edit_tutor);
        editTuturBtn.setOnClickListener(this);
        plusUserBtn = view.findViewById(R.id.fab_plus_tutor);
        plusUserBtn.setOnClickListener(this);
        menuBtn = view.findViewById(R.id.menu_profile_btn);
        menuBtn.setOnClickListener(this);
        backBtn = view.findViewById(R.id.back_from_my_profile);
        backBtn.setOnClickListener(this);
        setInfo(view);

        if (status == OWNER){
            plusUserBtn.setVisibility(View.GONE);
            editTuturBtn.setVisibility(View.VISIBLE);
            menuBtn.setVisibility(View.VISIBLE);
            titleFragment.setText("My profile");
        } else {
            titleFragment.setText("Profile");
            menuBtn.setVisibility(View.GONE);
            plusUserBtn.setVisibility(View.VISIBLE);
            editTuturBtn.setVisibility(View.GONE);
        }

//        Intent intent = getIntent();
//
//        statusUser = intent.getIntExtra("status", -1);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_edit_tutor:{
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                getActivity().startActivityForResult(intent, 3);
                break;
            }
            case R.id.menu_profile_btn:{
                popupMenu = new PopupMenu(getActivity(), view.findViewById(R.id.menu_profile_btn),
                        Gravity.TOP | Gravity.RIGHT);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu_profile, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                break;
            }
            case R.id.fab_plus_tutor:{
                break;
            }
            case R.id.back_from_my_profile:{
                getFragmentManager().popBackStack();
            }
            default:{
                break;
            }
        }
    }

    private void setInfo(View view){
        boolean ifTutor = false, ifAtHome = false;
        String fn = "", sn = "", patr = "", pn = "", addr = "";
        int prc = 0, yOfEduc = 0;
        ArrayList<String> sbj = new ArrayList<>();
        ArrayList<Education> ed = new ArrayList<>();

        switch (status){
            case OWNER:{
                ifTutor = Actor.getsInstance().ifTutor;
                fn = Actor.getsInstance().firstName;
                sn = Actor.getsInstance().secondName;
                //patr = Actor.getsInstance().patronymic;
                pn = Actor.getsInstance().telNumber;
                prc = Actor.getsInstance().cost;
                sbj = Actor.getsInstance().themes;
                ed = Actor.getsInstance().educations;
                ifAtHome = Actor.getsInstance().ifAtHome;
                addr = Actor.getsInstance().address;
                yOfEduc = Actor.getsInstance().yearOfEducation;

                backBtn.setVisibility(View.GONE);
                break;
            }
            case VISITERFRIEND:{
                if (user != null) {
                    ifTutor = !Actor.getsInstance().ifTutor;
                    fn = user.firstName;
                    sn = user.secondName;
                    //patr = user.patronymic;
                    pn = user.telNumber;
                    prc = user.cost;
                    sbj = user.themes;
                    ed = user.educations;
                    ifAtHome = user.ifAtHome;
                    addr = user.address;
                    yOfEduc = user.yearOfEducation;
                }
                plusUserBtn.setImageResource(R.drawable.email);
                backBtn.setVisibility(View.VISIBLE);
                break;
            }
            case VISITER:{
                if (user != null) {
                    ifTutor = !Actor.getsInstance().ifTutor;
                    fn = user.firstName;
                    sn = user.secondName;
                    //patr = user.patronymic;
                    prc = user.cost;
                    sbj = user.themes;
                    ed = user.educations;
                    ifAtHome = user.ifAtHome;
                    yOfEduc = user.yearOfEducation;
                }
                plusUserBtn.setImageResource(R.drawable.plus);
                backBtn.setVisibility(View.VISIBLE);
                break;
            }
        }
        if (ifTutor){
            title.setText("Tutor");
        } else {
            title.setText("Student");
        }

        if (fn.equals("")) {
            firstName.setVisibility(View.GONE);
        } else {firstName.setText(fn);}

        if (sn.equals("")) {
            secondName.setVisibility(View.GONE);
        } else {secondName.setText(sn);}

//        if (patr.equals("")) {
//            patronymic.setVisibility(View.GONE);
//        } else {patronymic.setText(patr);}

        if (yOfEduc == 0) {
            yearOfEducation.setVisibility(View.GONE);
        } else {
            yearOfEducation.setText(yOfEduc + " Class");}

        if (pn.equals("")) {
            phoneNumber.setVisibility(View.GONE);
            phoneNumberTitle.setVisibility(View.GONE);
        } else {phoneNumber.setText(pn);}

        if (ifAtHome) {
            if (status == OWNER || status == VISITERFRIEND){
                address.setText(addr);
            } else {
                address.setText("At home");
            }
        } else {address.setText("Going out");}

        if (prc == 0){
            price.setVisibility(View.GONE);
            priceTitle.setVisibility(View.GONE);
        } else {
            price.setText(prc + " rub");
        }

        if (sbj.size() == 0){
            subjects.setVisibility(View.GONE);
            subjectsTitle.setVisibility(View.GONE);
        } else {
            String res = "";
            for (int i = 0; i < sbj.size(); i ++){
                res += (i == 0) ? sbj.get(i) : (", " + sbj.get(i));
            }
            subjects.setText(res);
        }

        if (ed.size() == 0){
            educations.setVisibility(View.GONE);
            educationsTitle.setVisibility(View.GONE);
        } else {
            String res = "";
            for (int i = 0; i < ed.size(); i ++){
                String item = ed.get(i).nameOfUniversity + ", " + ed.get(i).faculty + ", "
                        + ed.get(i).yearOfEnd;
                res += (i == 0) ? item : ("\n" + item);
            }
            educations.setText(res);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_password:{
                return true;
            }
            case R.id.change_phone:{
                return true;
            }
            case R.id.exit:{
                Actor.getsInstance().clear();
                Controller.getsInstance().ifLogIn = false;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(intent, 1);
                return true;
            }
            default:{
                return false;
            }
        }
    }
}
