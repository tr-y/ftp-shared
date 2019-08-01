package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.FileSplit;
import java.util.List;

public interface FileSplitService {


   List<FileSplit>findListByFileId(String fileId);

   void insert(FileSplit fileSplit);

   void delete(int id);

   void update(FileSplit fileSplit);
}
