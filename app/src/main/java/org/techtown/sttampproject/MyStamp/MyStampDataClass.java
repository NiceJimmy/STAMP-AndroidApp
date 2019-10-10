package org.techtown.sttampproject.MyStamp;

import com.google.gson.annotations.SerializedName;

public class MyStampDataClass {


    @SerializedName("userID")
    private String user_id;

    @SerializedName("userID2")
    private String user_id2;

    @SerializedName("proName")
    private String proName;

    @SerializedName("start_time")
    private String StartTime;

    @SerializedName("finish_time")
    private String FinishTime;

    @SerializedName("off_rate")
    private String off_rate;

    @SerializedName("address")
    private String address;

    @SerializedName("address2")
    private String address2;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("stamp_num")
    private String stamp_num;

    @SerializedName("response")
    private String Response;


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

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        FinishTime = finishTime;
    }

    public String getOff_rate() {
        return off_rate;
    }

    public void setOff_rate(String off_rate) {
        this.off_rate = off_rate;
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

    public String getStamp_num() {
        return stamp_num;
    }

    public void setStamp_num(String stamp_num) {
        this.stamp_num = stamp_num;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public MyStampDataClass(String user_id, String user_id2, String proName, String startTime, String finishTime, String off_rate, String address, String address2, String shorder, String stamp_num, String response) {
        this.user_id = user_id;
        this.user_id2 = user_id2;
        this.proName = proName;
        this.StartTime = startTime;
        this.FinishTime = finishTime;
        this.off_rate = off_rate;
        this.address = address;
        this.address2 = address2;
        this.shorder = shorder;
        this.stamp_num = stamp_num;
        Response = response;



    }
}
