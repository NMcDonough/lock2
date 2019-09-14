package com.nrkey.lock.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="permissions")
public class Permission {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private String name;
	private boolean canEditUsers;
	private boolean canEditPages;
	private boolean canAccessModPage;
	private boolean canEditMods;
	private boolean canEditAdministrators;
	
	@JsonIgnore
	@OneToMany(mappedBy="permission")
    private List<User> users = new ArrayList<User>();
	
	public Permission() {}
	
	public Permission(String s, boolean editUsers, boolean editPages, boolean modPage, boolean mod, boolean sudo) {
		this.setName(s);
		this.setCanEditPages(editPages);
		this.setCanEditUsers(editUsers);
		this.setCanAccessModPage(modPage);
		this.setCanEditMods(mod);
		this.setCanEditAdministrators(sudo);
	}

	public boolean isCanEditAdministrators() {
		return canEditAdministrators;
	}

	public void setCanEditAdministrators(boolean canEditAdministrators) {
		this.canEditAdministrators = canEditAdministrators;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getCanEditPages() {
		return canEditPages;
	}

	public void setCanEditPages(boolean canEditPages) {
		this.canEditPages = canEditPages;
	}

	public boolean getCanEditUsers() {
		return canEditUsers;
	}

	public void setCanEditUsers(boolean canEditUsers) {
		this.canEditUsers = canEditUsers;
	}

	public boolean getCanAccessModPage() {
		return canAccessModPage;
	}

	public void setCanAccessModPage(boolean canAccessModPage) {
		this.canAccessModPage = canAccessModPage;
	}

	public boolean getCanEditMods() {
		return canEditMods;
	}

	public void setCanEditMods(boolean canEditMods) {
		this.canEditMods = canEditMods;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}