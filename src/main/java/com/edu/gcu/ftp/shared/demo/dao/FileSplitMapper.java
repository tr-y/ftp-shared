package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.FileSplit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FileSplitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileSplit record);

    int insertSelective(FileSplit record);

    FileSplit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileSplit record);

    int updateByPrimaryKeyWithBLOBs(FileSplit record);

    int updateByPrimaryKey(FileSplit record);

    List<FileSplit> findListByFileId(@Param(value = "fileId") String fileId);}