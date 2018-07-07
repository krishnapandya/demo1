package com.example.hp.brahmsamaj.retrofit;


import com.example.hp.brahmsamaj.vo.ErrorsResponse;
import com.example.hp.brahmsamaj.vo.ServiceResponse;

/**
 * Created by PRATIK on 08-Jun-16.
 */
public interface DataProviderResponse {
    void onDataProviderResult(ServiceResponse dataModel);
    void onDataProviderResult(ErrorsResponse errorsResponse);
}
