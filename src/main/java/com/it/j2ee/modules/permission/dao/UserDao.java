/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.it.j2ee.modules.permission.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.it.j2ee.modules.permission.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByUserName(String loginName);
}
