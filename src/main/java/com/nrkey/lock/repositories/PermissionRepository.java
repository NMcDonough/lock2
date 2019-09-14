package com.nrkey.lock.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nrkey.lock.models.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Optional<Permission> findById(Long id); 
    Optional<Permission> findByName(String name);
    List<Permission> findAll();
}