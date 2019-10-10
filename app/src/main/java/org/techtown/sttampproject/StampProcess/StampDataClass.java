package org.techtown.sttampproject.StampProcess;

import com.google.gson.annotations.SerializedName;

public class StampDataClass {


    @SerializedName("user_id")
    private String userID;

    @SerializedName("shorder")
    private String shorder;

    @SerializedName("count")
    private String count;

     @SerializedName("start_time")
     private String StartTime;

    @SerializedName("finish_time")
    private String FinishTime;

    @SerializedName("discount_rate")
    private String Discount;

    @SerializedName("response")
    private String Response;

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

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public StampDataClass(String userID, String shorder, String count, String startTime, String finishTime, String discount, String response) {
        this.userID = userID;
        this.shorder = shorder;
        this.count = count;
        StartTime = startTime;
        FinishTime = finishTime;
        Discount = discount;
        Response = response;



    }
}
