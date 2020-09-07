package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("user")
    @Expose
    private UserUpdate user;

    @SerializedName("company")
    @Expose
    private UserDetail userDetail;

    public UserUpdateResponse(boolean success, UserUpdate user, UserDetail userDetail) {
        this.success = success;
        this.user = user;
        this.userDetail = userDetail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserUpdate getUser() {
        return user;
    }

    public void setUser(UserUpdate user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
