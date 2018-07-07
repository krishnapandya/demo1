package com.example.hp.brahmsamaj.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.brahmsamaj.BaseActivity;
import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.activity.LoginActivityNew;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;
import com.example.hp.brahmsamaj.utility.Utility;
import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yugtia-pm on 3/27/2018.
 */

public class NetworkDataProvider {

    String ENDPOINT_URL = AppConstants.ApiConstant.MAIN_URL;
    private Context mContext;
    private DataProviderResponse responseListener;

    public NetworkDataProvider(Context mContext) {
        this.mContext = mContext;
    }

    public void setResponseListener(DataProviderResponse responseListener) {
        this.responseListener = responseListener;
    }

    public Retrofit callRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public void makeHttpCall(Object object, int apiCode, final boolean isLoaderRequired) {

        if (Utility.isConnectivityAvailable(mContext)) {

            final ProgressDialog pd = Utility.getProgressDialog(mContext, mContext.getResources().getString(R.string.msg_wait), false);

            if (isLoaderRequired)
                pd.show();

            APIInterface apiInterface = callRetrofit().create(APIInterface.class);

            Call<ServiceResponse> call = null;

            switch (apiCode) {
                case AppConstants.ApiConstant.LOGIN:
                    call = apiInterface.login(object);
                    break;
                case AppConstants.ApiConstant.SIGNUP:
                    call = apiInterface.registration(object);
                    break;

                case AppConstants.ApiConstant.GETPROFILE:
                    call = apiInterface.getMyProfile("Bearer  " + UserPreferenceManager.preferenceGetString(AppConstants.SharedPreferenceKey.USER_TOKEN, ""));
                    break;

                case AppConstants.ApiConstant.UPDATEMYPROFILE:
                    call = apiInterface.updateMyProfile("Bearer  " + UserPreferenceManager.preferenceGetString(AppConstants.SharedPreferenceKey.USER_TOKEN, ""), object);
                    break;

                case AppConstants.ApiConstant.LOGOUT:
                    call = apiInterface.logout(UserPreferenceManager.preferenceGetString(AppConstants.SharedPreferenceKey.USER_TOKEN, ""));
                    break;
                default:
                    if (pd.isShowing())
                        pd.dismiss();
                    call = null;
            }

            try {
                if (call != null) {

                    call.enqueue(new Callback<ServiceResponse>() {
                        @Override
                        public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                            try {
                                if (pd.isShowing())
                                    pd.dismiss();

                                int httpResponseCode;
                                if (response.body() != null) {
                                    ServiceResponse serviceResponse = response.body();
                                    httpResponseCode = serviceResponse.getStatus_code();
                                    if (httpResponseCode == 200) {
                                        responseListener.onDataProviderResult(serviceResponse);
                                    } else {
                                        ServiceResponse responseModel = new ServiceResponse();
                                        responseModel.setStatus_code(response.body().getStatus_code());
                                        responseModel.setSuccess(response.body().isSuccess());
                                        responseModel.setMessage(response.body().getMessage());
                                        responseListener.onDataProviderResult(responseModel);
                                    }

                                } else if (response.body() == null) {
                                    if (response.code() == 401) {
                                        Intent intent = new Intent(mContext, LoginActivityNew.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intent);
                                        UserPreferenceManager.clearPreference();
                                        ((BaseActivity) mContext).finish();
                                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                                    } else if (response.code() == 422) {
                                        Gson gson = new Gson();
                                        ErrorsResponse post = gson.fromJson(response.errorBody().string(), ErrorsResponse.class);
                                        responseListener.onDataProviderResult(post);
                                    } else if (response.code() == 500) {
                                        Toast.makeText(mContext, "" + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    this.onFailure(call, new Throwable());
                                }

                            } catch (Exception e) {
                                Log.d("Response->onSuccess", e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ServiceResponse> call, Throwable t) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                                ServiceResponse response = new ServiceResponse();
                                response.setStatus_code(0);
                                response.setMessage(response.getMessage());
                                Log.e("response failure:", response.getMessage());
                                //response.setMessage(mContext.getResources().getString(R.string.msg_connection_error));
                                responseListener.onDataProviderResult(response);
                            } catch (Exception e) {
                                Log.d("Response->onFailure", e.getMessage());
                            }
                        }
                    });
                } else {
                    if (pd.isShowing())
                        pd.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ServiceResponse responseModel = new ServiceResponse();
            responseModel.setStatus_code(0);
            //responseModel.setMessage(mContext.getResources().getString(R.string.msg_no_internet));
            responseModel.setMessage(mContext.getResources().getString(R.string.msg_no_internet));
            responseListener.onDataProviderResult(responseModel);
        }
    }

}
