package com.it.j2ee.modules.permission.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.it.j2ee.modules.permission.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {
}
