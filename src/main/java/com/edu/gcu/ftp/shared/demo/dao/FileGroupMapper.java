package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.FileGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FileGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileGroup record);

    int insertSelective(FileGroup record);

    FileGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileGroup record);

    int updateByPrimaryKey(FileGroup record);

    List findByGroupIdAndParent(@Param(value = "groupId")int groupId,@Param(value = "parent")int parent);
    List searchByOrder(@Param(value = "groupId")int groupId,@Param(value = "order")String  order,@Param(value = "state")int state);
    FileGroup findByName(@Param(value = "name")String name,@Param(value = "groupId")int  groupId,@Param(value = "parentId")int parentId);
    List findListByFileId(@Param(value = "fileId")int fileId);
}