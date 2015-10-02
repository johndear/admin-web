/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.it.j2ee.jms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.it.j2ee.modules.jms.simple.NotifyMessageListener;
import com.it.j2ee.modules.jms.simple.NotifyMessageProducer;
import com.it.j2ee.modules.permission.entity.User;
import com.it.j2ee.spring.SpringContextTestCase;
import com.it.j2ee.test.category.UnStable;
import com.it.j2ee.test.log.LogbackListAppender;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml", "/jms/applicationContext-jms-simple.xml" })
@ActiveProfiles("dev")
public class JmsSimpleTest extends SpringContextTestCase {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;

	@Test
	public void queueMessage() throws InterruptedException {
		Thread.sleep(1000);
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger(NotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		logger.info("sended message");

		Thread.sleep(1000);
		assertThat(appender.getFirstMessage()).isEqualTo("UserName:calvin, Email:calvin@sringside.org.cn");
	}

	@Test
	public void topicMessage() throws InterruptedException {
		Thread.sleep(1000);
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger(NotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendTopic(user);
		logger.info("sended message");

		Thread.sleep(1000);
		assertThat(appender.getFirstMessage()).isEqualTo("UserName:calvin, Email:calvin@sringside.org.cn");
	}
}
