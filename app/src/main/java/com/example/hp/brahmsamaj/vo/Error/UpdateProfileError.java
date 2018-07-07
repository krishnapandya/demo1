package com.example.hp.brahmsamaj.vo.Error;

import java.util.List;

public class UpdateProfileError {
    private List<String> name;
    private List<String> contact_number;
    private List<String> pin_code;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getContact_number() {
        return contact_number;
    }

    public void setContact_number(List<String> contact_number) {
        this.contact_number = contact_number;
    }

    public List<String> getPin_code() {
        return pin_code;
    }

    public void setPin_code(List<String> pin_code) {
        this.pin_code = pin_code;
    }
}
