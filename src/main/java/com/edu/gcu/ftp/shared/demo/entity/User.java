package com.edu.gcu.ftp.shared.demo.entity;

import java.io.Serializable;

public class User   implements Serializable {
    private Integer id;

    private String userId;

    private String password;

    private String nickname;

    private Integer ftpId;

    private Integer groupId;

    private Integer grantId;

    private Integer type;

    private Integer size;

    private Integer share_size;

    private UserFtp userFtp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getFtpId() {
        return ftpId;
    }

    public void setFtpId(Integer ftpId) {
        this.ftpId = ftpId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGrantId() {
        return grantId;
    }

    public void setGrantId(Integer grantId) {
        this.grantId = grantId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getShare_size() {
        return share_size;
    }

    public void setShare_size(Integer share_size) {
        this.share_size = share_size;
    }


    public UserFtp getUserFtp() {
        return userFtp;
    }

    public void setUserFtp(UserFtp userFtp) {
        this.userFtp = userFtp;
    }
}