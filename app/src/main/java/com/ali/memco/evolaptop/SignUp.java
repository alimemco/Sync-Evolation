package com.ali.memco.evolaptop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ali.memco.evolaptop.dataModel.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    private EditText first_name;
    private EditText last_name;
    private EditText age;
    private CheckBox java;
    private CheckBox html;
    private CheckBox css;
    private RadioButton male;
    private RadioButton female;
    private SwitchCompat job;
    private Button register;
    private static final String TAG = "SignUp";
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpUserData();

            }
        });

    }

    private void initViews() {
        first_name = findViewById(R.id.signUp_ed_txt_name);
        last_name = findViewById(R.id.signUp_ed_txt_family);
        age = findViewById(R.id.signUp_ed_txt_age);
        java = findViewById(R.id.signUp_chb_java);
        html = findViewById(R.id.signUp_chb_html);
        css = findViewById(R.id.signUp_chb_css);
        male = findViewById(R.id.signUp_rb_male);
        female = findViewById(R.id.signUp_rb_female);
        job = findViewById(R.id.signUp_switch_job);
        register = findViewById(R.id.signUp_send);


    }

    private void signUpUserData(){

        apiService = new ApiService(SignUp.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name",first_name.getText().toString());
            jsonObject.put("last_name",last_name.getText().toString());
            jsonObject.put("age",age.getText().toString());

            if (male.isChecked()){
                jsonObject.put("gender","male");
            }else {
                jsonObject.put("gender","female");
            }
            int jobs;
            if (job.isChecked()){
                jobs=1;
            }else {
                jobs=0;
            }
            jsonObject.put("has_job",jobs);

            JSONArray skillsarray = new JSONArray();
            if (java.isChecked()){
                skillsarray.put("java");
            }
            if (html.isChecked()){
                skillsarray.put("html");
            }

            if (css.isChecked()){
                skillsarray.put("css");
            }

            jsonObject.put("skills",skillsarray);

            apiService.SignupUser(jsonObject, new ApiService.onSignupUser() {
                @Override
                public void onSignup(boolean res) {
                    if (res){
                        Toast.makeText(SignUp.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUp.this, "خطا در ثبت نام", Toast.LENGTH_SHORT).show();

                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
