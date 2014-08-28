package com.ejushang.spider.domain;


import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 角色
 */
public class Role implements java.io.Serializable, Comparable<Role>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
    /**角色名*/
	private String name;

    /**用户是否拥有该角色 */
    private boolean isUR;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(Role myClass) {
		return new CompareToBuilder().append(this.name, myClass.name)
				.append(this.id, myClass.id).toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Role)) {
			return false;
		}
		Role rhs = (Role) object;
		return new EqualsBuilder().append(this.name, rhs.name)
				.append(this.id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-1903814475, 1107969487).append(this.name)
				.append(this.id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name)
				.append("id", this.id).toString();
	}

    public boolean isUR() {
        return isUR;
    }

    public void setUR(boolean UR) {
        isUR = UR;
    }

}
