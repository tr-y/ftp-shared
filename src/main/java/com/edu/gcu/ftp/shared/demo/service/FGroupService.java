package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.FGroup;
import java.util.List;

public interface FGroupService {


  int  addFGroup(FGroup fGroup);

  List searchByName(String name);

  List findList();
}
