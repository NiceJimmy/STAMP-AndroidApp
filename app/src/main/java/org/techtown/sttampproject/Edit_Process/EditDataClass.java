package org.techtown.sttampproject.Edit_Process;

import com.google.gson.annotations.SerializedName;

public class EditDataClass {

    @SerializedName("userID")
    private String user_id;

    @SerializedName("category")
    private String category;

    @SerializedName("proName")
    private String proName;

    @SerializedName("address")
    private String address;

    @SerializedName("address2")
    private String address2;

    @SerializedName("description")
    private String description;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("path")
    private String path;

    @SerializedName("store_tel1")
    private String store_tel1;

    @SerializedName("store_tel2")
    private String store_tel2;

    @SerializedName("pic_done")
    private String pic_done;

    @SerializedName("menu_done")
    private String menu_done;

    @SerializedName("stamp_done")
    private String stamp_done;

    @SerializedName("response")
    private String Response;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStore_tel1() {
        return store_tel1;
    }

    public void setStore_tel1(String store_tel1) {
        this.store_tel1 = store_tel1;
    }

    public String getStore_tel2() {
        return store_tel2;
    }

    public void setStore_tel2(String store_tel2) {
        this.store_tel2 = store_tel2;
    }

    public String getPic_done() {
        return pic_done;
    }

    public void setPic_done(String pic_done) {
        this.pic_done = pic_done;
    }

    public String getMenu_done() {
        return menu_done;
    }

    public void setMenu_done(String menu_done) {
        this.menu_done = menu_done;
    }

    public String getStamp_done() {
        return stamp_done;
    }

    public void setStamp_done(String stamp_done) {
        this.stamp_done = stamp_done;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
