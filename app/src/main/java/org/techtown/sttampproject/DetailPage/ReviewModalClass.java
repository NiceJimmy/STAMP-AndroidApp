package org.techtown.sttampproject.DetailPage;

import com.google.gson.annotations.SerializedName;

public class ReviewModalClass {

    @SerializedName("user_id")
    private String userID;

    @SerializedName("userID2")
    private String userID2;

    @SerializedName("shorder") // get을 하거나 데이터를 받아올때 이 명칭과 php $row 명칭과 일치해야한다.
    private String shorder;

    @SerializedName("count")
    private String count;

    @SerializedName("review")
    private String review;

    @SerializedName("rate")
    private String rate;

    @SerializedName("date")
    private String date;

    @SerializedName("response")
    private String Response;


    public ReviewModalClass(String userID, String userID2, String shorder, String count, String review, String rate, String date, String response) {
        this.userID = userID;
        this.userID2 = userID2;
        this.shorder = shorder;
        this.count = count;
        this.review = review;
        this.rate = rate;
        this.date = date;
        Response = response;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID2() {
        return userID2;
    }

    public void setUserID2(String userID2) {
        this.userID2 = userID2;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}

