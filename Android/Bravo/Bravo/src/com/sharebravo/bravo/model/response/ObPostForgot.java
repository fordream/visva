package com.sharebravo.bravo.model.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ObPostForgot {
    @SerializedName("data")
    public ArrayList<Data> data;
    @SerializedName("status")
    public int             status;
    @SerializedName("error")
    public String          error;

    public ObPostForgot() {

    }

    class Data {
    }
}
