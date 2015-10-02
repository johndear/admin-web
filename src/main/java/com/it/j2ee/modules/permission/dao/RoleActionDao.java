package com.it.j2ee.modules.permission.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.it.j2ee.modules.permission.entity.RoleAction;

public interface RoleActionDao extends PagingAndSortingRepository<RoleAction, Long> {

}
