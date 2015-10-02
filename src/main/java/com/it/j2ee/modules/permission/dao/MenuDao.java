package com.it.j2ee.modules.permission.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.it.j2ee.modules.permission.entity.Menu;

public interface MenuDao extends PagingAndSortingRepository<Menu, Long> {

}
