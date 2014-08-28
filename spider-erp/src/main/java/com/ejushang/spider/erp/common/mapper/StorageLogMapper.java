package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.domain.StorageLog;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: tin
 * Date: 14-04-10
 * Time: 下午18:49
 */
public interface StorageLogMapper {
      public Integer saveStorageLog(StorageLog storageLog);
}
