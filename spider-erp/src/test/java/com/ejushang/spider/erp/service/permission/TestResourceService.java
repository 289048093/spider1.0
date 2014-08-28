package com.ejushang.spider.erp.service.permission;

import com.ejushang.spider.domain.Operation;
import com.ejushang.spider.service.permission.IResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ejushang.spider.domain.Resource;

import java.util.ArrayList;
import java.util.List;


/**
 * User:moon
 * Date: 13-12-24
 * Time: 下午1:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-erp.xml")
public class TestResourceService {

    @javax.annotation.Resource
    private IResourceService resourceService;

    @Test
    public void saveResource(){
        Resource resource=new Resource();
        resource.setName("账单管理");
        resource.setUrl("/ck/one");
        resource.setUniqueKey("ck");
        resourceService.save(resource);
        System.out.println("======================ok");
    }

    @Test
    public void saveOperation(){
        Operation operation=new Operation();
        operation.setName("查询入库");
        operation.setUrl("/ck/two");
        operation.setConfigable(true);
        operation.setRequired("查询出库详情");
        operation.setResourceId(10);
        resourceService.saveOperation(operation);
        System.out.println("======================ok");
    }

    //@Test
    public void findResource(){
        Resource resource=new Resource();
        resource=resourceService.get(10);
        System.out.println(resource.getId()+"/"+resource.getName()+"/"+resource.getUrl()+"/"+resource.getUniqueKey());
        System.out.println("======================ok");
    }

    //@Test
    public void deleteResource(){
        resourceService.delete(10);
        System.out.println("======================ok");
    }

    @Test
    public void findAllResource(){
        List<Resource>   list=new ArrayList<Resource>();
        list=resourceService.findAll();
        for(Resource l:list){
            System.out.println(l.getId()+"/"+l.getName()+"/"+l.getUrl()+"/"+l.getUniqueKey());
        }
    }

    @Test
    public void findAllOperation(){
        List<Operation>   list=new ArrayList<Operation>();
        list=resourceService.findAllOperation();
//        for(Operation l:list){
//          l.setResourceName(resourceService.get(l.getResourceId()).getName());
//        }
        for(Operation l:list){
            System.out.println(l.getId()+"/"+l.getName()+"/"+l.getUrl()+"/"+l.getResourceId()+"/"+l.getResourceName());
        }
    }

    // @Test
    public void findOperationById(){
        Operation operation=resourceService.getOperationById(20);
        System.out.println(operation.getId()+"/"+operation.getName()+"/"+operation.getUrl()+"/"+operation.getResourceId()+"/"+operation.getConfigable()+"/"+operation.getRequired());
    }
}
