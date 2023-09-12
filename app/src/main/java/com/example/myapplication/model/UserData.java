package com.example.myapplication.model;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Table(name = "UserData")
public class UserData extends SugarRecord {
    @Unique
    private String userName;
    private String hashedUserPassword; // 存储哈希后的密码
    private String userProfilePicture = "";
    private String userReadNewsIds = "";
    private String userLikedNewsIds = "";
    private String userCommentedNewsIds = "";

    public UserData() {}

    public UserData(String userName, String userPassword) {
        this.userName = userName;
        this.hashedUserPassword = hashPassword(userPassword); // 对密码进行哈希处理
    }

    public String getUserName() {
        return userName;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public String getHashedUserPassword() {
        return hashedUserPassword;
    }

    public String getUserReadNewsIds() {
        return userReadNewsIds;
    }

    public void setUserReadNewsIds(List<String> userReadNewsIds) {
        this.userReadNewsIds = convertListToString(userReadNewsIds);
    }

    public String getUserLikedNewsIds() {
        return userLikedNewsIds;
    }

    public void setUserLikedNewsIds(List<String> userLikedNewsIds) {
        this.userLikedNewsIds = convertListToString(userLikedNewsIds);
    }

    public String getUserCommentedNewsIds() {
        return userCommentedNewsIds;
    }

    public void setUserCommentedNewsIds(List<String> userCommentedNewsIds) {
        this.userCommentedNewsIds = convertListToString(userCommentedNewsIds);
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public static void updateProfilePicture(String userName, String newProfilePictureUri) {
        UserData userData = UserData.find(UserData.class, "USER_NAME = ?", userName).get(0);
        userData.setUserProfilePicture(newProfilePictureUri);
        userData.save();
    }

    // 哈希密码的方法
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            // 将哈希字节数组转换为十六进制字符串
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // 处理异常
            return null;
        }
    }
    private String convertListToString(List<String> dataList) {
        // 将 List<String> 转换为 JSON 字符串
        // 这里使用 Gson 库进行序列化
        Gson gson = new Gson();
        return gson.toJson(dataList);
    }
}
