package com.ejushang.spider.erp.service.permission;

import com.ejushang.spider.common.mapper.OperationMapper;
import com.ejushang.spider.common.mapper.PermissionMapper;
import com.ejushang.spider.common.mapper.RolePermissionMapper;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.service.permission.IPermissionService;
import com.ejushang.spider.service.permission.IResourceService;
import com.ejushang.spider.service.permission.IRoleService;
import com.ejushang.spider.vo.ResourceVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBException;
import java.util.*;

public class PermissionServiceTest extends ErpTest {
	
	@Autowired
	private IPermissionService permissionService;

    @javax.annotation.Resource
    private OperationMapper operationMapper;

    @Autowired
    private IResourceService resourceService;

    @javax.annotation.Resource
    private RolePermissionMapper rolePermissionMapper;

    @javax.annotation.Resource
    private PermissionMapper permissionMapper;

    @Autowired
    private IRoleService roleService;

	@Test
	@Rollback(false)
	public void test() throws JAXBException {
        permissionService.refreshPermissions("permissions.xml", false);
    }

    @Test
    public void findAllPermission(){
//        List<Permission> list=permissionService.findByRole(5);
//        for(Permission l:list){
//            System.out.println(l.getId()+"/"+l.getOperationId()+"/"+l.getOperation().getName()+"/"+l.getOperation().getId()+"/"+l.getOperation().getResourceId());
//
//        }
        System.out.println("====================ok!");
        //查找所有的资源
        List<Operation> lists= operationMapper.findAll();
//        for(Operation ll:lists){
//
//            System.out.println(ll.getName()+"/"+ll.getId()+"/"+ll.getResourceId());
//
//        }

        //System.out.println("====================ok!");
        //查找所有的模块
        List<Resource> listss= resourceService.findAll();
//        for(Resource lll:listss){
//            System.out.println(lll.getName());
//        }
        /**
         *   /**
         * 查找角色下的所有权限
         * @param roleId
         * @return

        List<Permission> findByRole(int roleId);
         */
//        List<Permission> list1=permissionService.findByRole(5);
//        for(Permission l:list1){
//            System.out.println(l.getId()+"/"+l.getOperationId()+"/"+l.getOperation().getName()+"/"+l.getOperation().getId()+"/"+l.getOperation().getResourceId());
//
//        }
        //System.out.println("====================ok!");

       List<RolePermission> rolePermissionList= rolePermissionMapper.findByRole(4) ;
        List<Operation> operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
               Permission permission=permissionMapper.get(rpList.getPermissionId());
               Operation operation=operationMapper.get(permission.getOperationId());
               operationRoleList.add(operation); //一个角色拥有的操作
           // System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.getOp());
        }
//        for(Operation lll:operationRoleList){
//
//            System.out.println(lll.getName()+"/"+lll.getId()+"/"+lll.getResourceId());
//
//
//        }
        //System.out.println("====================ok!");

//        for(Operation ll:lists){
//
//            if(ll.getId().equals(operation.getId()))  {
//                ll.setOp();
//                System.out.println(ll.getOp());
//                operation.setOp(1);
//                System.out.println(operation.getOp());
//            }else{
//                ll.setOp(0);
//                operation.setOp(0);
//            }
//
//        }
        for(Operation operationList:lists){
            operationList.setOp(operationRoleList.contains(operationList));
          //System.out.println(operationList.isOp());
        }

        for(Resource resourceList:listss){
            List<Operation> listList=new ArrayList<Operation>();
            for(Operation operationList:lists ){
                if(operationList.getResourceId().equals(resourceList.getId())){
                    listList.add(operationList);
                }
            }
            Collections.sort(listList,new Comparator<Operation>() {
                @Override
                public int compare(Operation o1, Operation o2) {
                    if(o1.getConfigable()!=o2.getConfigable()){
                        return  1;
                    }
                    return 0;
                }
            });
            resourceList.setOperationList(listList);
            }

        for (Resource resourceList:listss){
            System.out.println(resourceList.getName());
            for(Operation oop:resourceList.getOperationList()){
              System.out.println(oop.getName()+"/"+oop.getId()+"/"+oop.isOp()+"/"+oop.getConfigable());
            }
        }
    }

//    /**
//     * 对角色授权
//     * @param roleId
//     * @param operationNames 操作名称
//     */
//    void grantPermissions(Integer roleId, Collection<String> operationNames);

    //@Test
    public void updatePermission(){

        List<RolePermission> rolePermissionList= rolePermissionMapper.findByRole(6) ;
        List<Operation> operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
        System.out.println("====================ok!");

       List<String> list=new ArrayList();
        list.add("查询用户");
        permissionService.grantPermissions(6,list);
         rolePermissionList= rolePermissionMapper.findByRole(6) ;
         operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
    }

    @Test
    public void saveRolePermission(){
        List<RolePermission> rolePermissionList= rolePermissionMapper.findByRole(6) ;
        List<Operation> operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
        System.out.println("====================ok!");

        int[] permissionId={7};
        Collection<String> s=  permissionService.updateStringList(permissionId);
        for(String ss:s){
           System.out.println(ss);
        }
        System.out.println("====================ok!");
        permissionService.grantPermissions(6,s);
        rolePermissionList= rolePermissionMapper.findByRole(6) ;
        operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
    }
    @Test
    public void insertRole(){
        Role role=new Role();
        role.setName("总经理助理");
        roleService.saveRole(role);
        System.out.println("====================="+role.getId());
        List<RolePermission> rolePermissionList= rolePermissionMapper.findByRole(role.getId()) ;
        List<Operation> operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
        System.out.println("====================ok!");
        int[] permissionId={7};
        Collection<String> s=  permissionService.updateStringList(permissionId);
        for(String ss:s){
            System.out.println(ss);
        }
        System.out.println("====================ok!");
        permissionService.grantPermissions(role.getId(),s);
        rolePermissionList= rolePermissionMapper.findByRole(role.getId()) ;
        operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
            System.out.println(operation.getName()+"/"+operation.getId()+"/"+operation.getResourceId()+"/"+operation.isOp());
        }
    }

    @Test
    public void testFindRoleResource(){

        List<ResourceVo> resourceList=permissionService.findRoleResource(52);

        for(ResourceVo resource:resourceList){
             System.out.println(resource.getName());
        }

    }

    @Test
    public void findLinkOperation(){

       // int[] intArray=permissionService.findLinkOperation(185);

//        for(Integer id:intArray){
//            System.out.println(id);
//        }
    }

    @Test
    public void pn(){

        List<String> integerList=new ArrayList<String>();

        integerList.add("false");
        integerList.add("true");
        integerList.add("true");
        integerList.add("true");
        Collections.sort(integerList,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                        if(o1!=o2 && o1.equals("false")){
                            return 1;
                        }
                        return 0;

            }
        });
        for(String i:integerList){
           System.out.println(i);
        }
    }

    @Test
    public void addpn(){

        List<Resource> resourceList=permissionService.addAllPermission();
        for (Resource resourceLit:resourceList){
            System.out.println(resourceLit.getName());
            for(Operation oop:resourceLit.getOperationList()){
                System.out.println(oop.getName()+"/"+oop.getId()+"/"+oop.isOp()+"/"+oop.getConfigable());
            }
        }
    }
}
