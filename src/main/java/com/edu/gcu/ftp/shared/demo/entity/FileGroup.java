package com.edu.gcu.ftp.shared.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class FileGroup {
    private Integer id;

    private Integer file_id;

    private Integer group_id = 0 ;

    private String name;

    private Integer user_id;

    private String path;

    private String type;

    private String state;

    private Integer parent;

    private String aias;

    private Integer size;

    private Date updatetime;

    private UserFtp userFtp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getAias() {
        return aias;
    }

    public void setAias(String aias) {
        this.aias = aias == null ? null : aias.trim();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public UserFtp getUserFtp() {
        return userFtp;
    }

    public void setUserFtp(UserFtp userFtp) {
        this.userFtp = userFtp;
    }
}