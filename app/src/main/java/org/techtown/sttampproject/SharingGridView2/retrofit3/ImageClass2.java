package org.techtown.sttampproject.SharingGridView2.retrofit3;

import com.google.gson.annotations.SerializedName;

public class ImageClass2 {


    @SerializedName("user_id")
    private String userID;

    @SerializedName("image_name")
    private String Title;

    @SerializedName("image")
    private String Image;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("picorder")
    private String picorder;

    @SerializedName("picorder2")
    private String picorder2;

    @SerializedName("picorder3")
    private String picorder3;

    @SerializedName("response")
    private String Response;

    public ImageClass2(String userID, String title, String image, String shorder, String picorder, String picorder2, String picorder3, String response) {
        this.userID = userID;
        this.Title = title;
        this.Image = image;
        this.shorder = shorder;
        this.picorder = picorder;
        this.picorder2 = picorder2;
        this.picorder3 = picorder3;
        Response = response;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getShorder() {
        return shorder;
    }

    public void setShorder(String shorder) {
        this.shorder = shorder;
    }

    public String getPicorder() {
        return picorder;
    }

    public void setPicorder(String picorder) {
        this.picorder = picorder;
    }

    public String getPicorder2() {
        return picorder2;
    }

    public void setPicorder2(String picorder2) {
        this.picorder2 = picorder2;
    }

    public String getPicorder3() {
        return picorder3;
    }

    public void setPicorder3(String picorder3) {
        this.picorder3 = picorder3;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
