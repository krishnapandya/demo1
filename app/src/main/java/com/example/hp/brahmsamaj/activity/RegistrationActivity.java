package com.example.hp.brahmsamaj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.brahmsamaj.BaseActivity;
import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.retrofit.DataProviderResponse;
import com.example.hp.brahmsamaj.retrofit.NetworkDataProvider;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;
import com.example.hp.brahmsamaj.utility.Utility;
import com.example.hp.brahmsamaj.vo.Error.RegisterError;
import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.Request.LoginRequest;
import com.example.hp.brahmsamaj.vo.Request.RegistartionRequest;
import com.example.hp.brahmsamaj.vo.Response.LoginResponse;
import com.example.hp.brahmsamaj.vo.Response.SignupResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * Created by HP on 07-06-2018.
 */

public class RegistrationActivity extends BaseActivity {


    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_confirmpass)
    EditText edt_confirmpass;
    @BindView(R.id.edt_contactno)
    EditText edt_contactno;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_signup)
    Button btn_signup;


    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_signup, frameLayout);
        ButterKnife.bind(this, view);
    }


    @OnClick({R.id.btn_signup})
    public void onViewClicked(View view) {

        String fullname = edt_fullname.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String contactno = edt_contactno.getText().toString().trim();
        String confirmpass = edt_confirmpass.getText().toString().trim();


        switch (view.getId()) {
            case R.id.btn_signup:
                Toast.makeText(this, "pass" + password + "or conpass" + confirmpass, Toast.LENGTH_SHORT).show();
                System.out.print("pass" + password + "or conpass" + confirmpass);
                if (TextUtils.isEmpty(fullname)) {
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_fullname));
                } else if (TextUtils.isEmpty(email)) {
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_email));
                } else if (TextUtils.isEmpty(contactno)) {
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_contactno));
                } else if (TextUtils.isEmpty(confirmpass)) {
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_confirmpass));
                } else if (TextUtils.isEmpty(password)) {
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_password));
                } else if (!password.equals(confirmpass)) {
                    System.out.print("pass" + password + "or conpass" + confirmpass);
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_matchpass));
                } else if (password.length() < 8 || confirmpass.length() < 8) {
                    System.out.print("pass" + password + "or conpass" + confirmpass);
                    Utility.showToast(RegistrationActivity.this, getResources().getString(R.string.err_passsize));
                } else {
                    signup(fullname, email, contactno, password, confirmpass, true);
                }
        }

    }

    public void signup(String fullname, String email, String contactno, String password, String confirmpass, boolean isLoaderRequired) {
        NetworkDataProvider networkDataProvider = new NetworkDataProvider(RegistrationActivity.this);
        RegistartionRequest request = new RegistartionRequest();
        request.setFullName(fullname);
        request.setEmail(email);
        request.setContact_number(contactno);
        request.setPassword(password);
        request.setConfirmPassword(confirmpass);

        networkDataProvider.setResponseListener(new DataProviderResponse() {
            Gson gson = new GsonBuilder().create();

            @Override
            public void onDataProviderResult(ServiceResponse response) {
                if (response.getStatus_code() == 200) {

                    SignupResponse signupResponse = gson.fromJson(gson.toJson(response.getData()), SignupResponse.class);
                    Toast.makeText(RegistrationActivity.this, "" + signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegistrationActivity.this, "Click", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivityNew.class);
                    startActivity(intent);
                    finish();
                    Paper.book().write("user_data", signupResponse);
                } else if (response.getStatus_code() == 422) {
                    Toast.makeText(RegistrationActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrationActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onDataProviderResult(ErrorsResponse errorsResponse) {
                RegisterError registerError = gson.fromJson(gson.toJson(errorsResponse.getErrors()), RegisterError.class);
                Toast.makeText(RegistrationActivity.this, "" + registerError.getConfirmPassword().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistrationActivity.this, "" + registerError.getFullName().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistrationActivity.this, "" + registerError.getEmail().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistrationActivity.this, "" + registerError.getPassword().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistrationActivity.this, "" + registerError.getContact_number().get(0), Toast.LENGTH_SHORT).show();
            }
        });

        networkDataProvider.makeHttpCall(request, AppConstants.ApiConstant.SIGNUP, isLoaderRequired);
    }
}

