package com.example.qlchdt.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

@SerializedName("userId")
@Expose
private String userId;
@SerializedName("hoten")
@Expose
private String hoten;
@SerializedName("taikhoan")
@Expose
private String taikhoan;
    @SerializedName("matkhau")
    @Expose
    private String matkhau;
@SerializedName("token")
@Expose
private String token;

/**
* No args constructor for use in serialization
*
*/
public User() {
}

/**
*
* @param taikhoan
* @param hoten
* @param userId
* @param token
*/
public User(String userId, String hoten, String taikhoan, String token) {
super();
this.userId = userId;
this.hoten = hoten;
this.taikhoan = taikhoan;
this.token = token;
}

    public User(String userId, String hoten, String taikhoan, String matkhau, String token) {
        this.userId = userId;
        this.hoten = hoten;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.token = token;
    }

    public User(String taikhoan, String matkhau) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", hoten='" + hoten + '\'' +
                ", taikhoan='" + taikhoan + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public User withUserId(String userId) {
this.userId = userId;
return this;
}

public String getHoten() {
return hoten;
}

public void setHoten(String hoten) {
this.hoten = hoten;
}

public User withHoten(String hoten) {
this.hoten = hoten;
return this;
}

public String getTaikhoan() {
return taikhoan;
}

public void setTaikhoan(String taikhoan) {
this.taikhoan = taikhoan;
}

public User withTaikhoan(String taikhoan) {
this.taikhoan = taikhoan;
return this;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public User withToken(String token) {
this.token = token;
return this;
}

}