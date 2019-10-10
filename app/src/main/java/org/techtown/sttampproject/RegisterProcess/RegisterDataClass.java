package org.techtown.sttampproject.RegisterProcess;

import com.google.gson.annotations.SerializedName;

public class RegisterDataClass {
    @SerializedName("userID")
    private String userID;

    @SerializedName("userPassword")
    private String userPassword;

    @SerializedName("userGender")
    private String userGender;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userProfile")
    private String userProfile;

    @SerializedName("response")
    private String Response;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
