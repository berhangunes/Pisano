package org.example;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Elements {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("index")
    @Expose
    private int index;

    public Elements() {

    }

    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }


    public String getType() {
        return type;
    }

}
