package com.mkyong.repository;

import org.springframework.data.repository.CrudRepository;

import com.mkyong.entity.FileMetaDataEntity;

public interface MetaDataRepository extends CrudRepository<FileMetaDataEntity, Long>{

}
