package com.example.bpwcurrencyModel;

public class User {
    private String name;
    private String email;
    private String password;
    private String phone;
    private double balanceAccount;
    private double balanceUSD;
    private double balanceSGD;
    private double balanceJPY;

    public User(String name, String email, String password, String phone, double balanceAccount, double balanceUSD, double balanceSGD, double balanceJPY) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.balanceAccount = balanceAccount;
        this.balanceUSD = balanceUSD;
        this.balanceSGD = balanceSGD;
        this.balanceJPY = balanceJPY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(float balanceAccount) {
        this.balanceAccount = balanceAccount;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }

    public void setBalanceUSD(float balanceUSD) {
        this.balanceUSD = balanceUSD;
    }

    public double getBalanceSGD() {
        return balanceSGD;
    }

    public void setBalanceSGD(float balanceSGD) {
        this.balanceSGD = balanceSGD;
    }

    public double getBalanceJPY() {
        return balanceJPY;
    }

    public void setBalanceJPY(float balanceJPY) {
        this.balanceJPY = balanceJPY;
    }
}
