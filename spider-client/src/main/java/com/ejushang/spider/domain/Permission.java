package com.ejushang.spider.domain;


import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 权限
 */
public class Permission implements java.io.Serializable, Comparable<Permission>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

     /** 操作id */
	private Integer operationId;
    //操作VO
	private transient Operation operation;

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	@Override
	public int compareTo(Permission myClass) {
		return new CompareToBuilder().append(this.operationId,
				myClass.operationId).append(this.operation, myClass.operation).append(this.id,
						myClass.id).toComparison();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Permission)) {
			return false;
		}
		Permission rhs = (Permission) object;
		return new EqualsBuilder().append(
				this.operationId, rhs.operationId).append(this.operation, rhs.operation).append(
				this.id, rhs.id).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-301303767, 1978533493).appendSuper(
				super.hashCode()).append(this.operationId)
				.append(this.operation).append(this.id).toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("operation", this.operation).append("id", this.id)
				.append("operationId", this.operationId).toString();
	}
	
	@Override
	public Object clone() {
		Permission p = new Permission();
		p.setId(this.id);
		p.setOperation((Operation) this.getOperation().clone());
		p.setOperationId(this.operationId);
		return p;
	}

}
