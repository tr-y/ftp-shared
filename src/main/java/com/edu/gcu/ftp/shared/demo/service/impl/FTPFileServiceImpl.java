package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.FtpFileMapper;
import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import com.edu.gcu.ftp.shared.demo.service.FTPFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FTPFileServiceImpl implements FTPFileService {

  @Autowired
  FtpFileMapper ftpFileMapper;


  public int insertFile(FtpFile ftpFile) {
    ftpFileMapper.insert(ftpFile);
    return ftpFile.getId() ;
  }

  @Override
  public int  findFileByUserId(String fileId) {
    FtpFile ftpFile;
   if(( ftpFile =ftpFileMapper.findFileByFileId(fileId))!=null){
     return ftpFile.getId();
   }
    return 0;
  }



  @Override
  public void update(FtpFile ftpFile) {
    ftpFileMapper.updateByPrimaryKeySelective(ftpFile);
  }

  @Override
  public void delete(int fileId) {
    ftpFileMapper.deleteByPrimaryKey(fileId);
  }

}
