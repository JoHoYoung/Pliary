package com.example.myapp.controller;

public class
UserConfirm {
    private boolean confirm;
    private String error;

    public UserConfirm(boolean confirm, String error) {
        this.confirm = confirm;
        this.error = error;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static final UserConfirm success(){
        return new UserConfirm(true, null);
    }

    public static final UserConfirm fail(String errMessage) {
        return new UserConfirm(false, errMessage);
    }
}
