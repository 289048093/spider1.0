package com.ejushang.spider.domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 *   操作
 */
public class Operation implements java.io.Serializable, Comparable<Operation>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
    /**操作路径 */
	private String url;
    /** 操作名字 */
	private String name;
    /** 是否把该操作加到操作界面 */
	private Boolean configable = false;
    /** 进行此操作的前提条件 */
	private String required;
    /** 资源id */
	private Integer resourceId;
    /** 资源name */
    private String resourceName;

    private boolean isOp;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public boolean isOp() {
        return isOp;
    }

    public void setOp(boolean op) {
        isOp = op;
    }

    public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getConfigable() {
		return this.configable;
	}

	public void setConfigable(Boolean configable) {
		this.configable = configable;
	}

	public String getRequired() {
		return this.required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public int compareTo(Operation myClass) {
		return new CompareToBuilder().append(this.id, myClass.id).toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Operation)) {
			return false;
		}
		Operation rhs = (Operation) object;
		return new EqualsBuilder().append(this.id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1596355739, -978009971).appendSuper(
				super.hashCode()).append(this.id).toHashCode();
	}

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", resourceId=" + resourceId +
                ", resourceName='" + resourceName + '\'' +
                '}';
    }
	
	@Override
	public Object clone() {
		Operation operation = new Operation();
		operation.setId(id);
		operation.setName(name);
		operation.setResourceId(resourceId);
		operation.setUrl(url);
		return operation;
	}



}
