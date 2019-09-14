package com.nrkey.lock.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nrkey.lock.models.Permission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="users")
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true)
    @Size(min=1, message = "Email is required!")
    @Email(message="Must be a valid email!")
    private String email;
	
    @Size(min=1, message = "Username is required!")
    @Column(unique = true)
    private String username;
    
    @Size(min=5, message="Password must be at least 5 characters!")
    private String password;
    
    private boolean isActive = true;
    
    @JsonIgnore
	@OneToMany(mappedBy="user")
    private List<Highscore> scores = new ArrayList<Highscore>();
    
    @Transient
    private String confirm;
    
    @Column(updatable=false)
    @CreationTimestamp
    private Date createdAt;
    
    private Date updatedAt;
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="permission_id")
    private Permission permission;
    
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public User() {}
	
	public User(String username, String email, String password, String confirm, Permission permission) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.confirm = confirm;
		this.permission = permission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
