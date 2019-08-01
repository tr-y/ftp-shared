package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FtpFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FtpFile record);

    int insertSelective(FtpFile record);

    FtpFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FtpFile record);

    int updateByPrimaryKey(FtpFile record);
    FtpFile findFileByFileId(@Param(value = "fileId") String fileId);

}