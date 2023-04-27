package com.aphy.caldavsyncadapter.android.entities;

import java.util.Objects;

public class AccountName {
    public static String AccountName = "AccountName";
    public static String Password = "Password";

    private String mAccountName;
    private String mPassword;

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        this.mAccountName = accountName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountName that = (AccountName) o;
        return Objects.equals(mAccountName, that.mAccountName) && Objects.equals(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAccountName, mPassword);
    }

    @Override
    public String toString() {
        return "AccountName{" +
                "mAccountName='" + mAccountName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }

    public void clearAccountInfo() {
        mAccountName = "";
        mPassword = "";
    }
}