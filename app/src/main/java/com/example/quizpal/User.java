package com.example.quizpal;

public class User {
    private String usernameBox, emailBox1, passwordBox1, referBox, profile;
    private long coins = 25;

    public User() {
    }

    public User(String usernameBox, String emailBox1, String passwordBox1, String referBox) {
        this.usernameBox = usernameBox;
        this.emailBox1 = emailBox1;
        this.passwordBox1 = passwordBox1;
        this.referBox = referBox;
    }

    public String getUsernameBox() {
        return usernameBox;
    }

    public void setUsernameBox(String usernameBox) {
        this.usernameBox = usernameBox;
    }

    public String getEmailBox1() {
        return emailBox1;
    }

    public String getPasswordBox1() {
        return passwordBox1;
    }

    public void setPasswordBox1(String passwordBox1) {
        this.passwordBox1 = passwordBox1;
    }

    public String getReferBox() {
        return referBox;
    }

    public void setReferBox(String referBox) {
        this.referBox = referBox;
    }


    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

