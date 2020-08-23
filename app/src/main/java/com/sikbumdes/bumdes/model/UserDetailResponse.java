package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("detail")
    @Expose
    private UserDetail userDetail;

    public UserDetailResponse(boolean success, User user, UserDetail userDetail) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
