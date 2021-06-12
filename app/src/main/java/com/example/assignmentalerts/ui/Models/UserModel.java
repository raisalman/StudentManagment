package com.example.assignmentalerts.ui.Models;

public class UserModel {
    String userName,email,pass,confirmPass;

    public UserModel(String userName, String email, String pass, String confirmPass) {
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.confirmPass = confirmPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
