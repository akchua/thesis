package com.thesis.tremor.beans;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.enums.UserType;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
public class UserBean extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -6785402734231625632L;
	
	private User user;

	public UserBean(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
		super(username, password, authorities);
		setUser(user);
	}

	public User getUserEntity() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return user.getId();
	}
	
	public String getFirstName() {
		return user.getFirstName();
	}
	
	public String getLastName() {
		return user.getLastName();
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public UserType getUserType() {
		return user.getUserType();
	}
	
	public Integer getItemsPerPage() {
		return user.getItemsPerPage();
	}
}
