package org.techtown.sttampproject.SharingGridView2.CountID;

import com.google.gson.annotations.SerializedName;

public class CountData {


        @SerializedName("userID")
        private String user_id;


        @SerializedName("response")
        private String Response;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
