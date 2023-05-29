package ddd.playground.sample.domain.entity;

import ddd.playground.sample.domain.value.UserMailAddress;

import java.util.List;

public class User {
    private String userId;
    private String userName;
    private List<UserMailAddress> mailAddressList;

    public User(String userId, String userName, List<UserMailAddress> mailAddressList) {
        this.userId = userId;
        this.userName = userName;
        this.mailAddressList = mailAddressList;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<UserMailAddress> getMailAddressList() {
        return mailAddressList;
    }
}
