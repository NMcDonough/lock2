package com.nrkey.lock.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nrkey.lock.models.Permission;
import com.nrkey.lock.repositories.PermissionRepository;

@Service
public class PermissionService {
	private final PermissionRepository permRepo;
	
	public PermissionService(PermissionRepository permRepo) {
		this.permRepo = permRepo;
	}
	
	public Permission makePermission(Permission perm) {
		return permRepo.save(perm);
	}

	public Object getAllPermissions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Permission> allPermissions() {
		return this.permRepo.findAll();
	}
	
	public Permission findById(Long id) {
		return this.permRepo.findById(id).get();
	}
	
	public void deletePermission(Long id) {
		this.permRepo.deleteById(id);
	}
	
	public Permission updatePermission(Permission p) {
		Optional<Permission> perm = this.permRepo.findById(p.getId());
		if(perm != null) {
			perm.get().setId(p.getId());
			perm.get().setCanEditUsers(p.getCanEditUsers());
			perm.get().setCanEditMods(p.getCanEditMods());
			perm.get().setUsers(p.getUsers());
			perm.get().setCanAccessModPage(p.getCanAccessModPage());
		}
		return this.permRepo.save(p);
	}
	
	public Permission findByName(String s) {
		return this.permRepo.findByName(s).get();
	}
}