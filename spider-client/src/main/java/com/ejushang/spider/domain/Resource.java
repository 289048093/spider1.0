package com.ejushang.spider.domain;


import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 资源
 */
public class Resource implements java.io.Serializable, Comparable<Resource>, Cloneable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
    /** 资源名 */
	private String name;
    /** 资源路径 */
	private String url;
    /** 唯一键 */
	private String uniqueKey;
    /** 前端展示需要使用的属性 */
    private String iconCls;
    /** 前端展示需要使用的属性 */
    private String module;
    /** 入口操作名称 */
    private String entryOperation;

    private List<Operation> operationList;

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUniqueKey() {
		return this.uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Object clone() {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setName(name);
		resource.setUrl(url);
		resource.setUniqueKey(uniqueKey);
        resource.setIconCls(iconCls);
        resource.setModule(module);
		return resource;
	}

	@Override
	public int compareTo(Resource myClass) {
		return new CompareToBuilder().append(this.url,myClass.url).append(this.name,
						myClass.name).append(this.id, myClass.id)
				.toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Resource)) {
			return false;
		}
		Resource rhs = (Resource) object;
		return new EqualsBuilder().append(this.url, rhs.url).append(this.name, rhs.name)
				.append(this.id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-854103471, 165885785).appendSuper(
				super.hashCode()).append(this.url).append(this.name)
				.append(this.id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("id", this.id)
                .append("url", this.url).toString();
	}

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getEntryOperation() {
        return entryOperation;
    }

    public void setEntryOperation(String entryOperation) {
        this.entryOperation = entryOperation;
    }

}
