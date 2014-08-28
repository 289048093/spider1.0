package com.ejushang.spider.erp.service.Repository;

import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.StorageQuery;
import com.ejushang.spider.service.delivery.IDeliveryService;
import com.ejushang.spider.service.repository.IStorageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
/**
 * User: tin
 * Date: 13-12-24
 * Time: 下午3:56
 */
public class StorageTest extends ErpTest {
    @Autowired
    private IStorageService storageService;

    @Autowired
    private IDeliveryService  deliveryService;



//    查询区
    @Test
    @Rollback(false)
    public void findStorage() {
        StorageQuery storage =new StorageQuery();
       storageService.findJoinStorage(storage);


    }

    @Test
    public void find(){
        System.out.println(storageService.findStorageByProdId(67));


    }

//    @Test
//    @Rollback(false)
//    public void findStorageById() {
//
//        Storage st = this.storageService.findStorageById(2);
//
//    }

    @Test
    @Rollback(false)
    public void findStorageByStorage() {

//        ProductQuery pro=new ProductQuery();
//       Page page=new Page();
//        page.setPageNo(1);
//        page.setPageSize(30);
//        pro.setCid(1);
//      page=storageService.findStorageByStorage(pro,page);



    }


    @Test
    public void testFindJoinStorageByPage() {
//        StorageQuery storageQuery = new StorageQuery();
//        // 构造分页信息
//        Page page = new Page();
//        // 设置当前页
//        page.setPageNo(1);
//        // 设置分页大小
//        page.setPageSize(15);
//        List<Storage> storageList = storageMapper.findJoinStorageByPage(storageQuery, page);
//        assertThat(storageList.size(), is(15));
//        assertThat(page.getResult().size(), is(15));
//
//        storageQuery.setProdCode(productMapper.findProductById(storageList.get(0).getProdId()).getProdCode());
//        storageList = storageMapper.findJoinStorageByPage(storageQuery, page);
//        assertThat(storageList.size(), is(1));
//        assertThat(page.getResult().size(), is(1));
    }
}
