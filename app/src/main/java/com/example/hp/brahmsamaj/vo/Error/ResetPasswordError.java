package com.example.hp.brahmsamaj.vo.Error;

import java.util.List;

public class ResetPasswordError {

    private List<String> oldpassword;
    private List<String> password;
    private List<String> confirmpassword;

    public List<String> getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(List<String> oldpassword) {
        this.oldpassword = oldpassword;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(List<String> confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
