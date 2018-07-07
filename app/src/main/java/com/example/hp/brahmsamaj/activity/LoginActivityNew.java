package com.example.hp.brahmsamaj.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.brahmsamaj.BaseActivity;
import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.retrofit.DataProviderResponse;
import com.example.hp.brahmsamaj.retrofit.NetworkDataProvider;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;
import com.example.hp.brahmsamaj.utility.Utility;
import com.example.hp.brahmsamaj.vo.Error.LoginError;
import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.Request.LoginRequest;
import com.example.hp.brahmsamaj.vo.Response.LoginResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class LoginActivityNew extends BaseActivity {

    @BindView(R.id.edt_login_email)
    EditText edtLoginEmail;
    @BindView(R.id.edt_login_password)
    EditText edtLoginPassword;
    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_signUp)
    TextView txtSignUp;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeVIew();
    }

    private void initializeVIew() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_login, frameLayout);
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.txt_forgot_password, R.id.btn_login, R.id.txt_signUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                break;
            case R.id.btn_login:
                userLogin(true, edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString());
                break;
            case R.id.txt_signUp:
                break;
        }
    }

    private void userLogin(boolean isLoaderRequired, String strEmail, String strPassword) {
        NetworkDataProvider networkDataProvider = new NetworkDataProvider(LoginActivityNew.this);

        LoginRequest request = new LoginRequest();
        request.setEmail(strEmail);
        request.setPassword(strPassword);

        networkDataProvider.setResponseListener(new DataProviderResponse() {

            Gson gson = new GsonBuilder().create();

            @Override
            public void onDataProviderResult(ServiceResponse response) {
                if (response.getStatus_code() == 200) {

                    LoginResponse loginResponse = gson.fromJson(gson.toJson(response.getData()), LoginResponse.class);
                    UserPreferenceManager.preferencePutString(AppConstants.SharedPreferenceKey.USER_TOKEN, loginResponse.getToken());
                    UserPreferenceManager.preferencePutBoolean(AppConstants.SharedPreferenceKey.IS_LOGGED_IN, true);
                    Toast.makeText(LoginActivityNew.this, "" + loginResponse.getToken(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivityNew.this, "Click", Toast.LENGTH_SHORT).show();
                    UserPreferenceManager.preferencePutBoolean(AppConstants.SharedPreferenceKey.IS_LOGGED_IN, true);
                    Utility.redirectToActivity(LoginActivityNew.this, HomeActivity.class, true, null, null, 0, null);
                    Paper.book().write("user_data", loginResponse);
                } else {
                    Toast.makeText(LoginActivityNew.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onDataProviderResult(ErrorsResponse errorsResponse) {
                if (errorsResponse.getStatus_code() == 422) {
                    LoginError loginError = gson.fromJson(gson.toJson(errorsResponse.getErrors()), LoginError.class);
                    if (loginError.getEmail() != null) {
                        Toast.makeText(LoginActivityNew.this, loginError.getEmail().get(0), Toast.LENGTH_SHORT).show();
                    }
                    if (loginError.getPassword() != null) {
                        Toast.makeText(LoginActivityNew.this, loginError.getPassword().get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        networkDataProvider.makeHttpCall(request, AppConstants.ApiConstant.LOGIN, isLoaderRequired);
    }
}
