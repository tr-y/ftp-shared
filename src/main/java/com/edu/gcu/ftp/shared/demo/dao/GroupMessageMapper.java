package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.GroupMessage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
@Component
public interface GroupMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupMessage record);

    int insertSelective(GroupMessage record);

    GroupMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupMessage record);

    int updateByPrimaryKeyWithBLOBs(GroupMessage record);

    List findListByGroupId(@RequestParam(value = "groupId") int groupId);

    int updateByPrimaryKey(GroupMessage record);

}