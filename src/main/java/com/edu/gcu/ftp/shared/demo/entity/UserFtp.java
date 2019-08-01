package com.edu.gcu.ftp.shared.demo.entity;

import java.io.Serializable;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.Mapper;


public class UserFtp implements Serializable {
    private Integer id;

    private String ftpId;

    private String ftp_pswd;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFtpId() {
        return ftpId;
    }

    public void setFtpId(String ftpId) {
        this.ftpId = ftpId == null ? null : ftpId.trim();
    }

    public String getFtp_pswd() {
        return ftp_pswd;
    }

    public void setFtp_pswd(String ftp_pswd) {
        this.ftp_pswd = ftp_pswd == null ? null : ftp_pswd.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}