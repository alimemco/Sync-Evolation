package com.ali.memco.evolaptop.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.SharedPrefrencesMan;
import com.ali.memco.evolaptop.dataModel.User;

public class Profile_Activity extends AppCompatActivity {

private Button edit_pro, saveSetting;
private ImageButton back;

private EditText ed_name, ed_family;
private CheckBox chb_java, chb_html , chb_css;
private RadioButton rb_male, rb_female;
private TextView moshakhsat, takhasos, jensiyat;

private User user = new User();
private SharedPrefrencesMan prefMan;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        initViews();
        setupToolbar();

        prefMan = new SharedPrefrencesMan(this);
        user=prefMan.getUser();

        getSharedprefUser();


        edit_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = edit_pro.getText().toString();
                Toast.makeText(Profile_Activity.this, txt, Toast.LENGTH_SHORT).show();

            }
        });

/*
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });*/

        chb_java.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setJavaExpert(isChecked);
            }
        });

                chb_html.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setHtmlExpert(isChecked);
            }
        });

                chb_css.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setCssExpert(isChecked);
            }
        });

        rb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    user.setGender(User.MALE);
                }else {
                    user.setGender(User.FEMALE);
                }
            }
        });

        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFirstName(ed_name.getText().toString());
                user.setLastName(ed_family.getText().toString());
                prefMan.saveUser(user);
                Toast.makeText(Profile_Activity.this, "تنظیمات ذخیره شد", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setupToolbar() {

        toolbar = findViewById(R.id.profile_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i) instanceof TextView){
                ((TextView) toolbar.getChildAt(i)).setTypeface(MainActivity.iranSans);
            }

        }
    }

    private void getSharedprefUser() {

        ed_name.setText(user.getFirstName());
        ed_family.setText(user.getLastName());
        chb_java.setChecked(user.isJavaExpert());
        chb_html.setChecked(user.isHtmlExpert());
        chb_css.setChecked(user.isCssExpert());

        byte gender = user.getGender();
        if (gender==User.MALE){
            rb_male.setChecked(true);
        }else {
            rb_female.setChecked(true);
        }
    }

    private void initViews() {
        edit_pro = findViewById(R.id.profile_btn_change_pro);
        ed_name = findViewById(R.id.ed_txt_name);
        ed_family = findViewById(R.id.ed_txt_family);
        chb_java = findViewById(R.id.chb_java);
        chb_html = findViewById(R.id.chb_html);
        chb_css = findViewById(R.id.chb_css);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        saveSetting = findViewById(R.id.profile_btn_save);
        moshakhsat = findViewById(R.id.pro_txt_moshakhasat);
        takhasos = findViewById(R.id.pro_txt_takhasos);
        jensiyat = findViewById(R.id.pro_txt_jensiyat);



        edit_pro.setTypeface(MainActivity.iranSans);
        ed_name.setTypeface(MainActivity.iranSans);
        ed_family.setTypeface(MainActivity.iranSans);
        chb_java.setTypeface(MainActivity.iranSans);
        chb_html.setTypeface(MainActivity.iranSans);
        chb_css.setTypeface(MainActivity.iranSans);
        rb_male.setTypeface(MainActivity.iranSans);
        rb_female.setTypeface(MainActivity.iranSans);
        saveSetting.setTypeface(MainActivity.iranSans);
        moshakhsat.setTypeface(MainActivity.iranSans);
        takhasos.setTypeface(MainActivity.iranSans);
        jensiyat.setTypeface(MainActivity.iranSans);
    }
}
