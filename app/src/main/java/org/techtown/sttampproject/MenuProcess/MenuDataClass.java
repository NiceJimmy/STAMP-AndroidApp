package org.techtown.sttampproject.MenuProcess;

import com.google.gson.annotations.SerializedName;

public class MenuDataClass {

    @SerializedName("user_id")
    private String userID;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("count")
    private String count;

    @SerializedName("menu")
    private String menu;

    @SerializedName("price")
    private String price;

    @SerializedName("response")
    private String Response;

    public MenuDataClass(String userID, String shorder, String count, String menu, String price, String response) {
        this.userID = userID;
        this.shorder = shorder;
        this.count = count;
        this.menu = menu;
        this.price = price;
        Response = response;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getShorder() {
        return shorder;
    }

    public void setShorder(String shorder) {
        this.shorder = shorder;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
