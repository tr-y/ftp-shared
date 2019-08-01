package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import java.util.List;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@Mapper
public interface FileUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileUser record);

    int insertSelective(FileUser record);

    FileUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileUser record);

    int updateByPrimaryKeyWithBLOBs(FileUser record);

    int updateByPrimaryKey(FileUser record);

    List<FTPFile> searchByUserpath(@Param(value = "id")int id,@Param(value = "path")String path);
//    int insertFTPFile(@Param(value = "list") List<FTPFile> ftpFile);

    List<FileUser>findListById(@Param(value = "userId")int userId,@Param(value = "parent")int parent);

    List<FileUser>searchByOrder(@Param(value = "order")String order,@Param(value = "userId")int userId,@Param(value = "state")int state);
    FileUser findByName(@Param(value = "name") String name,@Param(value = "userId") int userId,@Param(value = "parentId") int parentId);

}