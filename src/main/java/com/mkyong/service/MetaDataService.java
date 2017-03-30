package com.mkyong.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.mkyong.controller.UploadController;
import com.mkyong.model.FileMetaData;

@Service
public class MetaDataService {
	Logger logger = LoggerFactory.getLogger(MetaDataService.class);
  @Autowired
  private JdbcTemplate jdbcTemplate;
    
  public int saveToDB(FileMetaData metadata){
	logger.debug("filesize, lastaccesstime" + metadata.getFileSize() +", "+ metadata.getLastAccessTime());
    String sql = "INSERT INTO filemetadata(filesize, lastaccesstime) VALUES(?,?)";
   // return jdbcTemplate.update(sql, metadata.getIsDirectory(), metadata.getIsRegularFile(),  metadata.getFileSize(), metadata.getLastAccessTime(), metadata);
    return jdbcTemplate.update(sql,  metadata.getFileSize(), metadata.getLastAccessTime());    
  }
  
  public List<FileMetaData> getMetaData(){
	  
    return jdbcTemplate.query("SELECT * FROM filemetadata", new RowMapper<FileMetaData>(){

  public FileMetaData mapRow(ResultSet rs, int arg1) throws SQLException {
	FileMetaData p = new FileMetaData();
	
	p.setFileSize(rs.getInt("filesize"));
	p.setLastAccessTime(rs.getString("lastaccesstime"));
	logger.debug("metadata :"+p.toString());
	return p;
   }
      
    });
  }
}
