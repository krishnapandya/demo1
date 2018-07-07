package com.example.hp.brahmsamaj.vo.Response;

/**
 * Created by krishna on 6/12/2018.
 */

public class SignupResponse {

    boolean success;
    String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
