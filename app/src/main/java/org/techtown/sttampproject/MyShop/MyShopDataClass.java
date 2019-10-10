package org.techtown.sttampproject.MyShop;

import com.google.gson.annotations.SerializedName;

public class MyShopDataClass {

    @SerializedName("userID")
    private String user_id;

    @SerializedName("userID2")
    private String user_id2;

    @SerializedName("proName")
    private String proName;

    @SerializedName("address")
    private String address;

    @SerializedName("address2")
    private String address2;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("path")
    private String path;

    @SerializedName("stamp_num")
    private String stamp_num;

    @SerializedName("stamp_use")
    private String stamp_use;

    @SerializedName("response")
    private String Response;

    public MyShopDataClass(String user_id, String user_id2, String proName, String address, String address2, String shorder, String path, String stamp_num, String stamp_use, String response) {
        this.user_id = user_id;
        this.user_id2 = user_id2;
        this.proName = proName;
        this.address = address;
        this.address2 = address2;
        this.shorder = shorder;
        this.path = path;
        this.stamp_num = stamp_num;
        this.stamp_use = stamp_use;
        Response = response;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(String user_id2) {
        this.user_id2 = user_id2;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getShorder() {
        return shorder;
    }

    public void setShorder(String shorder) {
        this.shorder = shorder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStamp_num() {
        return stamp_num;
    }

    public void setStamp_num(String stamp_num) {
        this.stamp_num = stamp_num;
    }

    public String getStamp_use() {
        return stamp_use;
    }

    public void setStamp_use(String stamp_use) {
        this.stamp_use = stamp_use;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
