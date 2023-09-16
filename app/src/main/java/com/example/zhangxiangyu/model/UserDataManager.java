package com.example.zhangxiangyu.model;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDataManager {
    private static UserDataManager instance;
    private String userName;
    private String userProfilePicture;
    private List<String> userReadNewsIds;
    private List<String> userLikedNewsIds;
    private List<String> userCommentedNewsIds;

    // 私有构造函数，确保只有一个实例
    private UserDataManager() {
        userName = "Guest";
        userProfilePicture = "";
        userReadNewsIds = new ArrayList<>();
        userLikedNewsIds = new ArrayList<>();
        userCommentedNewsIds = new ArrayList<>();
    }

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    public UserData getUser() {
        List<UserData> users = UserData.find(UserData.class, "USER_NAME = ?", userName);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public List<String> getUserReadNewsIds() {
        if (userReadNewsIds == null) {
            userReadNewsIds = new ArrayList<>();
        }
        return userReadNewsIds;
    }

    public void setUserReadNewsIds(String userReadNewsIds) {
        this.userReadNewsIds = convertStringToList(userReadNewsIds);
    }

    public List<String> getUserLikedNewsIds() {
        return userLikedNewsIds;
    }

    public void setUserLikedNewsIds(String userLikedNewsIds) {
        if (!userLikedNewsIds.isEmpty()){
            this.userLikedNewsIds = convertStringToList(userLikedNewsIds);
        }
    }

    public List<String> getUserCommentedNewsIds() {
        return userCommentedNewsIds;
    }

    public void setUserCommentedNewsIds(String userCommentedNewsIds) {
        this.userCommentedNewsIds = convertStringToList(userCommentedNewsIds);
    }

    // 在退出登录时调用此方法重置为默认值
    public void resetToGuest() {
        userName = "Guest";
        userProfilePicture = "";
        userReadNewsIds = new ArrayList<>();
        userLikedNewsIds = new ArrayList<>();
        userCommentedNewsIds = new ArrayList<>();
    }

    private List<String> convertStringToList(String jsonString) {
        // 将 JSON 字符串转换为 List<String>
        // 这里使用 Gson 库进行反序列化
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }
}
