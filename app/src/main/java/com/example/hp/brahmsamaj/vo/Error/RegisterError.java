package com.example.hp.brahmsamaj.vo.Error;

import java.util.List;

public class RegisterError {
    private List<String> fullName;
    private List<String> email;
    private List<String> password;
    private List<String> contact_number;
    private List<String> confirmPassword;

    public List<String> getFullName() {
        return fullName;
    }

    public void setFullName(List<String> fullName) {
        this.fullName = fullName;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getContact_number() {
        return contact_number;
    }

    public void setContact_number(List<String> contact_number) {
        this.contact_number = contact_number;
    }

    public List<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(List<String> confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
