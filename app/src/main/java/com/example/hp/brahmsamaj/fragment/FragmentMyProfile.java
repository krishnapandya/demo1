package com.example.hp.brahmsamaj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.retrofit.DataProviderResponse;
import com.example.hp.brahmsamaj.retrofit.NetworkDataProvider;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;
import com.example.hp.brahmsamaj.vo.Error.UpdateProfileError;
import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.Request.UpdateMyProfileRequest;
import com.example.hp.brahmsamaj.vo.Response.GetMyProfileResponse;
import com.example.hp.brahmsamaj.vo.Response.UpdateMyProfileResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by HP on 07-06-2018.
 */

public class FragmentMyProfile extends Fragment {
    @BindView(R.id.edt_profile_screen_name)
    EditText edtProfileScreenName;
    @BindView(R.id.edt_profile_mobile)
    EditText edtProfileMobile;
    @BindView(R.id.edt_profile_first_name)
    EditText edtProfileFirstName;
    @BindView(R.id.edt_profile_middle_name)
    EditText edtProfileMiddleName;
    @BindView(R.id.edt_profile_last_name)
    EditText edtProfileLastName;
    @BindView(R.id.spinner_profile_gender)
    Spinner spinnerProfileGender;
    @BindView(R.id.edt_profile_birth_date)
    EditText edtProfileBirthDate;
    @BindView(R.id.edt_profile_gotra)
    EditText edtProfileGotra;
    @BindView(R.id.edt_profile_aadhar_number)
    EditText edtProfileAadharNumber;
    @BindView(R.id.spinner_occupation)
    Spinner spinnerOccupation;
    @BindView(R.id.edt_profile_occupation_desc)
    EditText edtProfileOccupationDesc;
    @BindView(R.id.edt_profile_pincode)
    EditText edtProfilePincode;
    @BindView(R.id.edt_profile_address)
    EditText edtProfileAddress;
    @BindView(R.id.btn_profile_submit)
    Button btnProfileSubmit;
    Unbinder unbinder;
    @BindView(R.id.edt_profile_birth_place)
    EditText edtProfileBirthPlace;
    ArrayAdapter<CharSequence> adapterGender;
    ArrayAdapter<CharSequence> adapterOccupation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        adapterGender = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfileGender.setAdapter(adapterGender);

        adapterOccupation = ArrayAdapter.createFromResource(getActivity(), R.array.occupation, android.R.layout.simple_spinner_item);
        adapterOccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOccupation.setAdapter(adapterOccupation);
        getMyProfile(true);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_profile_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_profile_submit:
                String gender = spinnerProfileGender.getSelectedItem().toString();
                String occupation = spinnerOccupation.getSelectedItem().toString();
                updateMyProfile(true, gender, occupation);
                break;
        }
    }

    public void getMyProfile(boolean isLoaderRequired) {
        NetworkDataProvider networkDataProvider = new NetworkDataProvider(getContext());
        networkDataProvider.setResponseListener(new DataProviderResponse() {
            Gson gson = new GsonBuilder().create();
            @Override
            public void onDataProviderResult(ServiceResponse response) {
                GetMyProfileResponse getMyProfileResponse = gson.fromJson(gson.toJson(response.getData()), GetMyProfileResponse.class);
                if (response.getStatus_code() == 200) {
                    if (!TextUtils.isEmpty(getMyProfileResponse.getAadhar_number()) && getMyProfileResponse.getAadhar_number() != null) {
                        edtProfileAadharNumber.setText(getMyProfileResponse.getAadhar_number());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getAddress()) && getMyProfileResponse.getAddress() != null) {
                        edtProfileAddress.setText(getMyProfileResponse.getAddress());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getBirth_date()) && getMyProfileResponse.getBirth_date() != null) {
                        edtProfileBirthDate.setText(getMyProfileResponse.getBirth_date());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getBirth_place()) && getMyProfileResponse.getBirth_place() != null) {
                        edtProfileBirthPlace.setText(getMyProfileResponse.getBirth_place());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getContact_number()) && getMyProfileResponse.getContact_number() != null) {
                        edtProfileMobile.setText(getMyProfileResponse.getContact_number());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getFirst_name()) && getMyProfileResponse.getFirst_name() != null) {
                        edtProfileFirstName.setText(getMyProfileResponse.getFirst_name());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getLast_name()) && getMyProfileResponse.getLast_name() != null) {
                        edtProfileLastName.setText(getMyProfileResponse.getLast_name());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getOccupation()) && getMyProfileResponse.getOccupation() != null) {
                        for (int i = 1; i < adapterOccupation.getCount(); i++) {
                            if (getMyProfileResponse.getOccupation().equalsIgnoreCase(adapterOccupation.getItem(i).toString())) {
                                spinnerOccupation.setSelection(i);
                            }
                        }
                      /*  int spinnerPosition = adapterOccupation.getPosition(getMyProfileResponse.getOccupation());
                        spinnerOccupation.setSelection(spinnerPosition);*/
                        //spinnerOccupation.setSsetText(getMyProfileResponse.getOccupation());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getOccupation_description()) && getMyProfileResponse.getOccupation_description() != null) {
                        edtProfileOccupationDesc.setText(getMyProfileResponse.getOccupation_description());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getPin_code()) && getMyProfileResponse.getPin_code() != null) {
                        edtProfilePincode.setText(getMyProfileResponse.getPin_code());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getMiddle_name()) && getMyProfileResponse.getMiddle_name() != null) {
                        edtProfileMiddleName.setText(getMyProfileResponse.getMiddle_name());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getName()) && getMyProfileResponse.getName() != null) {
                        edtProfileScreenName.setText(getMyProfileResponse.getName());
                    }
                    if (!TextUtils.isEmpty(getMyProfileResponse.getGender()) && getMyProfileResponse.getGender() != null) {
                        int spinnerPosition = adapterGender.getPosition(getMyProfileResponse.getGender());
                        spinnerProfileGender.setSelection(spinnerPosition);
                    }
                } else {
                    Toast.makeText(getContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDataProviderResult(ErrorsResponse errorsResponse) {

            }
        });
        networkDataProvider.makeHttpCall(null, AppConstants.ApiConstant.GETPROFILE, isLoaderRequired);
    }

    public void updateMyProfile(boolean isLoaderRequired, String strGender, String strOccupation) {
        NetworkDataProvider networkDataProvider = new NetworkDataProvider(getContext());
        UpdateMyProfileRequest updateMyProfileRequest = new UpdateMyProfileRequest();
        updateMyProfileRequest.setAddress(edtProfileAddress.getText().toString());
        updateMyProfileRequest.setContact_number(edtProfileMobile.getText().toString());
        updateMyProfileRequest.setName(edtProfileScreenName.getText().toString());
        updateMyProfileRequest.setFirst_name(edtProfileFirstName.getText().toString());
        updateMyProfileRequest.setMiddle_name(edtProfileMiddleName.getText().toString());
        updateMyProfileRequest.setLast_name(edtProfileLastName.getText().toString());
        updateMyProfileRequest.setAadhar_number(edtProfileAadharNumber.getText().toString());
        updateMyProfileRequest.setBirth_date(edtProfileBirthDate.getText().toString());
        updateMyProfileRequest.setBirth_place(edtProfileBirthPlace.getText().toString());
        updateMyProfileRequest.setGender(strGender);
        updateMyProfileRequest.setOccupation(strOccupation);
        updateMyProfileRequest.setOccupation_description(edtProfileOccupationDesc.getText().toString());
        updateMyProfileRequest.setGotra(edtProfileGotra.getText().toString());
        updateMyProfileRequest.setPin_code(edtProfilePincode.getText().toString());

        networkDataProvider.setResponseListener(new DataProviderResponse() {
            Gson gson = new GsonBuilder().create();
            @Override
            public void onDataProviderResult(ServiceResponse response) {
                if (response.getStatus_code() == 200) {
                    UpdateMyProfileResponse updateMyProfileResponse = gson.fromJson(gson.toJson(response.getData()), UpdateMyProfileResponse.class);
                    UserPreferenceManager.preferencePutString(AppConstants.SharedPreferenceKey.USER_TOKEN, updateMyProfileResponse.getToken());
                    Toast.makeText(getContext(), "" + updateMyProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataProviderResult(ErrorsResponse errorsResponse) {
                UpdateProfileError updateProfileError = gson.fromJson(gson.toJson(errorsResponse.getErrors()), UpdateProfileError.class);
                Toast.makeText(getActivity(), "" + updateProfileError.getName().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "" + updateProfileError.getContact_number().get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "" + updateProfileError.getPin_code().get(0), Toast.LENGTH_SHORT).show();
            }
        });
        networkDataProvider.makeHttpCall(updateMyProfileRequest, AppConstants.ApiConstant.UPDATEMYPROFILE, isLoaderRequired);
    }
}
