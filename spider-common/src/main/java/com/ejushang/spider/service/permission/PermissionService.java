package com.ejushang.spider.service.permission;

import com.ejushang.spider.domain.*;
import com.ejushang.spider.common.mapper.OperationMapper;
import com.ejushang.spider.common.mapper.PermissionMapper;
import com.ejushang.spider.common.mapper.RolePermissionMapper;
import com.ejushang.spider.jaxb.Permissions;
import com.ejushang.spider.service.user.IUserService;
import com.ejushang.spider.vo.ResourceVo;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.*;


/**
 * User: liubin
 * Date: 13-12-13
 */
@Service
public class PermissionService implements IPermissionService {

	private final Logger log = LoggerFactory.getLogger(PermissionService.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IResourceService resourceService;

    @javax.annotation.Resource
    private PermissionMapper permissionMapper;

    @javax.annotation.Resource
    private RolePermissionMapper rolePermissionMapper;

    @javax.annotation.Resource
    private OperationMapper operationMapper;


    /**
	 * 根据permissions.xml导入权限
	 * @param fileName
	 * @param updateRoles
	 */
	@Override
    @Transactional
	public boolean refreshPermissions(String fileName, boolean updateRoles) {

        Permissions.User pUser;
        Permissions permissions;
        try {
            JAXBContext jc = JAXBContext.newInstance(Permissions.class.getPackage().getName());
            permissions = (Permissions)jc.createUnmarshaller().unmarshal(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));

            pUser = permissions.getUser();
        } catch (JAXBException e) {
            log.error("读取权限xml的时候发生错误", e);
            return false;
        }

        List<User> users = userService.findRootUser();
        if(users.isEmpty()) {

			Role role = roleService.findRoleByName(IRoleService.ADMINISTRATOR_ROLE_NAME);
			if(role == null) {
				role = new Role();
				role.setName(IRoleService.ADMINISTRATOR_ROLE_NAME);
				roleService.saveRole(role);
			}

            User user = new User();
			user.setUsername(pUser.getUsername());
			user.setRootUser(true);
			String password = "111111";
			user.setPassword(password);
			user.setRoleId(role.getId());
            user.setRepoId(0);
			userService.saveUser(user);

		}

		//1.首先遍历所有resource与operation,检验配置是否有效
		//1)所有resource与operation的name必须唯一,不能重复
		//2)operation所有required的name必须存在
		//2.比对数据库中的resource列表与现在文件中的resource列表,查找哪些resource已经在新文件中被删除,从数据库中删除这些resource
		//3.处理所有的resource,查找哪些resource下的operation已经在新文件中被删除,从数据库中删除这些operation
		//4.逐条处理resource
		//5.逐条处理operation,首先递归处理required中的operation.然后判断该operation是新增还是修改.
		//1)如果是新增,直接数据库新增operation就好
		//2)如果是修改,找到之前数据库operation的required列表,比对required中的哪些operation需要删除和新增.
		//	忽略删除的,处理新增的.查询哪些角色拥有该operation的权限,将对应角色与新增的operation记录到一个map中,key为角色id,value为要赋予的权限列表
		//6.最后,根据map为角色赋予新的权限.
		//
		List<Permissions.Resource> pResources = permissions.getResource();
		//key为name,value为Permissions.Resource对象
		Map<String, Permissions.Resource> pResourceMap = new LinkedHashMap<String, Permissions.Resource>();
		//key为name,value为Permissions.Resource.Operation对象
		Map<String, Permissions.Resource.Operation> pOperationNameMap = new LinkedHashMap<String, Permissions.Resource.Operation>();
        //key为url, value为Permissions.Resource.Operation对象,只做校验用
        Map<String, Permissions.Resource.Operation> pOperationUrlMap = new HashMap<String, Permissions.Resource.Operation>();
		//key为name,value为Resource对象
		Map<String, Resource> resourceMap = new LinkedHashMap<String, Resource>();
		//key为name,value为Operation对象
		Map<String, Operation> operationMap = new LinkedHashMap<String, Operation>();
		//key为operation的name,value为resource对象的id
		Map<String, Integer> operationNameResourceIdMap = new HashMap<String, Integer>();
		//key为role对象的id,value为需要为该role新增operation的name
		Map<Integer, Set<String>> permissionToRoleMap = new HashMap<Integer, Set<String>>();

		//检验所有resource与operation的name必须唯一,不能重复
        //检验operation的url必须唯一,不能重复
		for(Permissions.Resource pResource : pResources) {
			assertNotExist(pResourceMap, pResource.getName(), "resource的name");
			pResourceMap.put(pResource.getName(), pResource);
			List<Permissions.Resource.Operation> pOperations = pResource.getOperation();
			if(pOperations.isEmpty()) {
				throw new RuntimeException(String.format("resource[%s] 的operation为空:", pResource.getName()));
			}
			for(Permissions.Resource.Operation pOperation : pOperations) {
				assertNotExist(pOperationNameMap, pOperation.getName(), "operation的name");
                assertNotExist(pOperationUrlMap, pOperation.getUrl(), "operation的url");
				pOperationNameMap.put(pOperation.getName(), pOperation);
                pOperationUrlMap.put(pOperation.getUrl(), pOperation);
			}
		}

		//检验operation所有required的name必须存在
		for(Map.Entry<String, Permissions.Resource.Operation> entry : pOperationNameMap.entrySet()) {
			Permissions.Resource.Operation pOperation = entry.getValue();
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					if(!pOperationNameMap.containsKey(requiredName)) {
						throw new RuntimeException(String.format("operation[%s]required的[%s]不存在", pOperation.getName(), requiredName));
					}
				}
			}
		}

        for(Permissions.Resource pResource : pResources) {
            String entryOperation = pResource.getEntryOperation();
            if(StringUtils.isBlank(entryOperation)) {
                throw new RuntimeException("entryOperation属性不能为空,resource name:" + pResource.getName());
            }
            if(pOperationNameMap.get(entryOperation) == null) {
                throw new RuntimeException("entryOperation对应的operation不存在,entryOperation:" + entryOperation);
            }
        }

		//比对数据库中的resource列表与现在文件中的resource列表,查找哪些resource已经在新文件中被删除,从数据库中删除这些resource
		List<Resource> resources = resourceService.findAll();
		for(Resource resource : resources) {
			if(!pResourceMap.containsKey(resource.getName())) {
				log.info("从数据库中删除resource[{}]", resource.getName());
				deleteResource(resource);
			} else {
				resourceMap.put(resource.getName(), resource);
			}
		}

		//查找哪些resource下的operation已经在新文件中被删除,从数据库中删除这些operation
		List<Operation> operations = resourceService.findAllOperation();
		for(Operation operation : operations) {
			if(!pOperationNameMap.containsKey(operation.getName())) {
				log.info("从数据库中删除operation[{}]", operation.getName());
				deleteOperation(operation);
			}
		}

		//逐条处理resource
		for(Permissions.Resource pResource : pResources) {
			Resource resource = resourceMap.get(pResource.getName());
			if(resource == null) {
				//新增resource
				resource = new Resource();
				resourceMap.put(pResource.getName(), resource);
				resource.setName(pResource.getName());
			}
			resource.setUniqueKey(pResource.getUniqueKey());
            resource.setUrl("");
            resource.setIconCls(pResource.getIconCls());
            resource.setModule(pResource.getModule());
            resource.setEntryOperation(pResource.getEntryOperation());
			resourceService.save(resource);

			List<Permissions.Resource.Operation> pOperations = pResource.getOperation();
			for(Permissions.Resource.Operation pOperation : pOperations) {
				operationNameResourceIdMap.put(pOperation.getName(), resource.getId());
			}
		}

		//逐条处理operation,首先递归处理required中的operation.然后判断该operation是新增还是修改.
		//1)如果是新增,直接数据库新增operation就好
		//2)如果是修改,找到之前数据库operation的required列表,比对required中的哪些operation需要删除和新增.
		//	忽略删除的,处理新增的.查询哪些角色拥有该operation的权限,将对应角色与新增的operation记录到一个map中,key为角色id,value为要赋予的权限列表
		operations = resourceService.findAllOperation();

		for(Operation operation : operations) {
			operationMap.put(operation.getName(), operation);
		}

		POperationHandler pOperationHandler = new POperationHandler(operationMap, pOperationNameMap,
				operationNameResourceIdMap, permissionToRoleMap);

		for(Map.Entry<String, Permissions.Resource.Operation> entry : pOperationNameMap.entrySet()) {
			Permissions.Resource.Operation pOperation = entry.getValue();
			pOperationHandler.handlePOperation(pOperation);
		}

		if(updateRoles) {
			//更新角色与角色所拥有的权限
			//1.首先校验配置是否正确
			//1)role的name是否有重复
			//2)operation引用的name是否都存在,并且是否都为configable=true
			//2.比对数据库中的role,查看哪些role需要删除(需要排除管理员角色,如果待删除的role有用户已经选择,则报异常),删除这些role以及对应的权限
			//3.根据role引用的operation,递归得到role拥有的所有operation,将对应角色与operation记录到一个map中,key为角色name,value为要赋予的权限列表
			//4.删除所有的权限
			//5.将所有权限设置到之前的permissionToRoleMap中

			Map<String, Permissions.Role> pRoleMap = new HashMap<String, Permissions.Role>();
			Map<String, Role> roleMap = new HashMap<String, Role>();
			//key为role的name,value为要授予role权限的operation name,这个map的权限最后与前面的permissionToRoleMap合并
			Map<String, Set<String>> permissionToRoleNameMap = new HashMap<String, Set<String>>();

			List<Permissions.Role> pRoles = permissions.getRole();
			for(Permissions.Role pRole : pRoles) {
				//检验role的name是否有重复
				assertNotExist(pRoleMap, pRole.getName(), "role的name");
				pRoleMap.put(pRole.getName(), pRole);
				List<Permissions.Role.Operation> rOperations = pRole.getOperation();
				//该角色的权限集合
				Set<String> permissionSet = new HashSet<String>();
				for(Permissions.Role.Operation rOperation : rOperations) {
					//得到引用的operation
					Permissions.Resource.Operation pOperation = pOperationNameMap.get(rOperation.getRef());
					//校验operation引用的name是否都存在,并且是否都为configable=true
					if("false".equalsIgnoreCase(pOperation.getConfigable())) {
						throw new RuntimeException(String.format("role[%s]引用了configable=false的operation[%s]", pRole.getName(), pOperation.getName()));
					}
					//递归得到该operation所有required的权限
					NavigableSet<String> ops = new TreeSet<String>();
					ops.add(pOperation.getName());
					while(!ops.isEmpty()) {
						String operationName = ops.pollFirst();
						permissionSet.add(operationName);
						pOperation = pOperationNameMap.get(operationName);
						if(!StringUtils.isBlank(pOperation.getRequired())) {
							String[] requiredOperations = pOperation.getRequired().split(",");
							for(String requiredOperation : requiredOperations) {
								ops.add(requiredOperation);
							}
						}
					}
				}
				permissionToRoleNameMap.put(pRole.getName(), permissionSet);
			}
			//此时已经得到角色拥有的所有权限,所以可以清除原来因为operation新增required所应该赋给角色的权限
			permissionToRoleMap.clear();

			//比对数据库中的role,查看哪些role需要删除(需要排除管理员角色,如果待删除的role有用户已经选择,则报异常),删除这些role以及对应的权限
			List<Role> roles = roleService.findAllRole();
			for(Role role : roles) {
				if(IRoleService.ADMINISTRATOR_ROLE_NAME.equals(role.getName())) continue;
				if(!pRoleMap.containsKey(role.getName())) {
					log.info("从数据库中删除role[{}]", role.getName());
//					try {
						roleService.deleteRoleById(role.getId());
//					} catch (AppException e) {
//						throw new RuntimeException(e);
//					}
				} else {
					roleMap.put(role.getName(), role);
				}
			}

			//逐条处理role
			for(Map.Entry<String, Permissions.Role> entry : pRoleMap.entrySet()) {
				String roleName = entry.getKey();
				Role role = roleMap.get(roleName);
				if(role == null) {
					role = new Role();
					role.setName(roleName);
					roleService.saveRole(role);
				}
				//此时将permissionToRoleNameMap里的权限设置到permissionToRoleMap,方便最后统一处理
				Set<String> permissionToRoleOperations = permissionToRoleNameMap.get(roleName);
				if(permissionToRoleOperations != null && !permissionToRoleOperations.isEmpty()) {
					permissionToRoleMap.put(role.getId(), permissionToRoleOperations);
				}
			}

			//删除所有的权限
			deleteAllRolePermissions();
		}

		//最后,根据map为角色赋予新的权限.
		for(Map.Entry<Integer, Set<String>> entry : permissionToRoleMap.entrySet()) {
			Integer roleId = entry.getKey();
			Set<String> operationNames = entry.getValue();
			if(operationNames != null && !operationNames.isEmpty()) {
				grantPermissions(roleId, operationNames);
			}
		}

        return true;
	}

	@Override
    @Transactional
	public void deleteAllRolePermissions() {
        rolePermissionMapper.deleteAll();
	}

	private void assertNotExist(Map<String, ?> map, String key, String label) {
		if(map.containsKey(key)) {
			throw new RuntimeException(String.format(label + "重复,key:[%s]", key));
		}
	}

	@Override
    @Transactional
	public void deleteResource(Resource resource) {
		List<Operation> operations = resourceService.findOperationByResource(resource.getId());
		for(Operation operation : operations) {
			deleteOperation(operation);
		}
		resourceService.delete(resource.getId());
	}

	@Override
    @Transactional
	public void deleteOperation(Operation operation) {
		rolePermissionMapper.deleteByOperation(operation.getId());
		permissionMapper.deleteByOperation(operation.getId());
		operationMapper.delete(operation.getId());
	}

    /**
     *  给角色授权，根据roleId和operationNames
     * @param roleId
     * @param operationNames 操作名称
     */
	@Override
    @Transactional
	public void grantPermissions(Integer roleId, Collection<String> operationNames) {
		List<RolePermission> rolePermissions = rolePermissionMapper.findByRole(roleId);
        if(operationNames!=null){
		List<Permission> permissions = permissionMapper.findByOperationName(operationNames.toArray(new String[]{}));
		Set<Integer> ownedPermissions = new HashSet<Integer>();
		for(RolePermission rp : rolePermissions) {
			ownedPermissions.add(rp.getPermissionId());
		}
		for(Permission p : permissions) {
			if(!ownedPermissions.contains(p.getId())) {
				RolePermission rp = new RolePermission();
				rp.setPermissionId(p.getId());
				rp.setRoleId(roleId);
				saveRolePermission(rp);
			}
		}
       }
	}

    @Override
    @Transactional
    public void save(Permission permission) {
        if(permission.getId() == null || permission.getId() == 0) {
            permissionMapper.save(permission);
        } else {
            permissionMapper.update(permission);
        }
    }

    @Override
    @Transactional
    public void saveRolePermission(RolePermission rolePermission) {
        if(rolePermission.getId() == null || rolePermission.getId() == 0) {
            rolePermissionMapper.save(rolePermission);
        } else {
            rolePermissionMapper.update(rolePermission);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findByRole(int roleId) {
        return permissionMapper.findByRole(roleId);
    }

    private class POperationHandler {
		Map<String, Operation> operationMap;
		Map<String, Permissions.Resource.Operation> pOperationNameMap;
		Map<String, Integer> operationNameResourceIdMap;
		Map<Integer, Set<String>> permissionToRoleMap;
		//集合中存放着已经处理过的operation的name,防止无限递归
		Set<String> handledPOperationNameSet = new HashSet<String>();
		//key为operation的name,value为对应operation的required属性中新增的operation name
		Map<String, Set<String>> operationNewlyRequiredMap = new HashMap<String, Set<String>>();

		public POperationHandler(
				Map<String, Operation> operationMap,
				Map<String, Permissions.Resource.Operation> pOperationNameMap,
				Map<String, Integer> operationNameResourceIdMap,
				Map<Integer, Set<String>> permissionToRoleMap) {
			super();
			this.operationMap = operationMap;
			this.pOperationNameMap = pOperationNameMap;
			this.operationNameResourceIdMap = operationNameResourceIdMap;
			this.permissionToRoleMap = permissionToRoleMap;
		}

		private void handlePOperation(Permissions.Resource.Operation pOperation) {
			if(handledPOperationNameSet.contains(pOperation.getName())) {
				//已经处理过了
				return;
			}
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					//递归处理required
					handlePOperation(pOperationNameMap.get(requiredName));
				}
			}
			Operation operation = operationMap.get(pOperation.getName());
			Integer resourceId = operationNameResourceIdMap.get(pOperation.getName());
			boolean isNew = operation == null;
			if(isNew) {
				//新增operation
				operation = new Operation();
				operation.setName(pOperation.getName());
				operationMap.put(pOperation.getName(), operation);
			} else {
				//修改operation
				String newlyRequired = pOperation.getRequired();
				String formerlyRequired = operation.getRequired();
				if(!StringUtils.isBlank(newlyRequired)) {
					//新增的required权限
					Set<String> newlyRequiredNames = Sets.newHashSet(newlyRequired.split(","));
					//之前的required权限
					Set<String> formerlyRequiredNames = null;
					if(!StringUtils.isBlank(formerlyRequired)) {
						formerlyRequiredNames = Sets.newHashSet(formerlyRequired.split(","));
					}
					if(formerlyRequiredNames != null && !formerlyRequiredNames.isEmpty()) {
						for(Iterator<String> iter = newlyRequiredNames.iterator(); iter.hasNext();) {
							String newlyRequiredName = iter.next();
							if(formerlyRequiredNames.contains(newlyRequiredName)) {
								iter.remove();
							}
						}
					}
					if(!newlyRequiredNames.isEmpty()) {
						operationNewlyRequiredMap.put(operation.getName(), newlyRequiredNames);
					}
					//得到当前operation的required(递归搜索)的新增权限集合
					Set<String> newlyPermissionToRoleOperations = getAllNewlyOperation(pOperation);

					//查询哪些role拥有当前operation权限
					if(!newlyPermissionToRoleOperations.isEmpty()) {
						List<Role> roles = roleService.findRoleByOperation(operation.getId());
						if(!roles.isEmpty()) {
							for(Role role : roles) {
								Set<String> permissionToRoleOperations = permissionToRoleMap.get(role.getId());
								if(permissionToRoleOperations == null) {
									permissionToRoleOperations = new HashSet<String>();
									permissionToRoleMap.put(role.getId(), permissionToRoleOperations);
								}
								permissionToRoleOperations.addAll(newlyPermissionToRoleOperations);
							}
						}
					}
				}
			}
			operation.setRequired(pOperation.getRequired());
			operation.setUrl(pOperation.getUrl());
			operation.setResourceId(resourceId);
			operation.setConfigable(!"false".equalsIgnoreCase(pOperation.getConfigable()));
			resourceService.saveOperation(operation);

			if(isNew) {
				Permission p = new Permission();
				p.setOperationId(operation.getId());
				save(p);
			}

			//记录到已处理列表
			handledPOperationNameSet.add(pOperation.getName());
		}

		private Set<String> getAllNewlyOperation(Permissions.Resource.Operation pOperation) {
			Set<String> results = new HashSet<String>();
			Set<String> newlyRequiredOperation = operationNewlyRequiredMap.get(pOperation.getName());
			if(newlyRequiredOperation != null) {
				results.addAll(newlyRequiredOperation);
			}
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					results.addAll(getAllNewlyOperation(pOperationNameMap.get(requiredName)));
				}
			}
			return results;
		}
	}

    /**
     * 查找出所有的操作，根据roleId标记相应的操作
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    public List<Resource> findAllPermission(Integer roleId) {

        //查找所有的资源
        List<Operation> listOperation= operationMapper.findAll();

        //查找所有的模块
        List<Resource> listResource= resourceService.findAll();
        List<Operation> operationRoleList=new ArrayList<Operation>();

        //一个角色拥有的操作
        operationRoleList=findRoleOperate(roleId);

        //如果角色拥有哪个角色，就把操作里的isOp设置成true，否则为false
        for(Operation operationList:listOperation){
            operationList.setOp(operationRoleList.contains(operationList));
        }

        //把操作加到相应的资源模块中（Resource）
        for(Resource resourceList:listResource){
            List<Operation> listList=new ArrayList<Operation>();
            for(Operation operationList:listOperation ){
                if(operationList.getResourceId().equals(resourceList.getId())){
                    listList.add(operationList);
                }
            }
            Collections.sort(listList,new Comparator<Operation>() {
                @Override
                public int compare(Operation o1, Operation o2) {
                    boolean same = o1.getConfigable()&&o2.getConfigable();
                    if( !same && !o1.getConfigable()){
                        return  1;
                    }
                    return 0;
                }
            });
            resourceList.setOperationList(listList);
        }

        return listResource;

    }

    /**
     * 查找出所有的操作,为添加页面用
     * @param
     * @return
     */
    @Override
    @Transactional
    public List<Resource> addAllPermission() {

        //查找所有的资源
        List<Operation> listOperation= operationMapper.findAll();

        //查找所有的模块
        List<Resource> listResource= resourceService.findAll();

        //把操作加到相应的资源模块中（Resource）
        for(Resource resourceList:listResource){
            List<Operation> listList=new ArrayList<Operation>();
            for(Operation operationList:listOperation ){
                if(operationList.getResourceId().equals(resourceList.getId())){
                    listList.add(operationList);

                }
            }
            Collections.sort(listList,new Comparator<Operation>() {
                @Override
                public int compare(Operation o1, Operation o2) {
                    boolean same = o1.getConfigable()&&o2.getConfigable();
                    if( !same && !o1.getConfigable()){
                        return  1;
                    }
                    return 0;
                }
            });
            resourceList.setOperationList(listList);
        }

        return listResource;

    }

    @Override
    @Transactional
    public Collection<String> updateStringList(int[] permissionId) {

        if(log.isInfoEnabled()){
                    log.info("修改或授权时传过来的permissionId为："+permissionId);
        }
        Collection<String> names=new ArrayList<String>();
        if(permissionId!=null){
        for(int id:permissionId){
            String name=operationMapper.get(id).getName();
            operationMapper.get(id).setOp(true);
            names.add(name);
           }
        }else{
            names=null;
        }
        return names;
    }

    @Override
    @Transactional
    public void changeOp(Integer roleId) {

        //一个角色拥有的操作
       List<Operation> operationList=findRoleOperate(roleId);
        for(Operation operation:operationList){
            rolePermissionMapper.deleteByRole(roleId);
        }
    }

    /**
     * 一个用户可以看见的模块
     * @param
     * @return
     */
     @Override
     @Transactional
    public List<ResourceVo> findRoleResource(Integer id){
         if(log.isInfoEnabled()){
                    log.info("查询用户能够进行操作的模块，登陆的用户id为："+id);
         }
         User user=userService.findUserById(id);
         if(user==null){
            return null;
         }
         //一个角色拥有的操作
         List<Resource> resourceList=new ArrayList<Resource>();
         if(user.isRootUser()){
             List<Operation>  operationList=operationMapper.findAll();
             resourceList=resourceService.findAll();
         }else{
             List<Operation>  operationList=findRoleOperate(user.getRoleId());
            List<String> stringList=new ArrayList<String>();
        for(Operation operation:operationList){
             stringList.add(operation.getName());
             Resource resource=resourceService.get(operation.getResourceId());
             if(!resourceList.contains(resource) && stringList.contains(resource.getEntryOperation())){
                 resourceList.add(resource);
             }
           }
         }
         List<ResourceVo> resourceVoList=new ArrayList<ResourceVo>();
         for(Resource resource:resourceList){
            ResourceVo resourceVo=new ResourceVo();
             resourceVo.setName(resource.getName());
             resourceVo.setIconCls(resource.getIconCls());
             resourceVo.setModule(resource.getModule());
             resourceVoList.add(resourceVo);
         }
       return resourceVoList;
    }

    /**
     * 一个角色拥有的操作
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    public List<Operation> findRoleOperate(Integer roleId) {
        if(log.isInfoEnabled()){
            log.info("查询一个角色拥有的操作权限时，传过来的roleId为："+roleId);
        }
        //一个角色拥有的操作
        List<RolePermission> rolePermissionList= rolePermissionMapper.findByRole(roleId) ;
        List<Operation> operationRoleList=new ArrayList<Operation>();
        for(RolePermission rpList:rolePermissionList){
            Permission permission=permissionMapper.get(rpList.getPermissionId());
            Operation operation=operationMapper.get(permission.getOperationId());
            operationRoleList.add(operation); //一个角色拥有的操作
        }
        Collections.sort(operationRoleList,new Comparator<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                if(o1.getConfigable()!=o2.getConfigable()){
                    return  1;
                }
                return 0;
            }
        });
        return operationRoleList;
    }

    @Override
    @Transactional
    public List<Integer> findLinkOperation(Integer id,String status) {
        if(log.isInfoEnabled()){
            log.info("传过来的id为："+id+"传过来的status为："+status);
        }
         List<Integer> integerList=new ArrayList<Integer>();
        Operation operation=operationMapper.get(id);
        if(status.equals("true")){
        String required=operation.getRequired();
        if(required==null){
            return integerList;
        }
        String[] requiredArray=required.split(",");

        for(int i=0;i<requiredArray.length;i++){
            Operation operation1=operationMapper.findOperationByName(requiredArray[i]);
            integerList.add(operation1.getId());
         }
        }else if(status.equals("false")){
           List<Operation> operationList=operationMapper.findAll();
            if(log.isInfoEnabled()){
                log.info("传过来的status为："+status);
            }
            for(Operation operation2:operationList){
                if(StringUtils.isNotBlank(operation2.getRequired())){
                String[] requiredArray=operation2.getRequired().split(",");
                for(String s:requiredArray){
                   if(s.equals(operation.getName())){
                      integerList.add(operation2.getId());
                   }
                  }
                }
            }
        }
        return integerList;
    }
}
