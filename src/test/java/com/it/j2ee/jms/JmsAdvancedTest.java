/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.it.j2ee.jms;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.it.j2ee.modules.jms.advanced.AdvancedNotifyMessageListener;
import com.it.j2ee.modules.jms.advanced.AdvancedNotifyMessageProducer;
import com.it.j2ee.modules.permission.entity.User;
import com.it.j2ee.spring.SpringContextTestCase;
import com.it.j2ee.test.category.UnStable;
import com.it.j2ee.test.log.LogbackListAppender;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml", "/jms/applicationContext-jms-advanced.xml" })
@ActiveProfiles("dev")
public class JmsAdvancedTest extends SpringContextTestCase {

	@Autowired
	private AdvancedNotifyMessageProducer notifyMessageProducer;

	@Resource
	private JmsTemplate advancedJmsTemplate;

	@Resource
	private Destination advancedNotifyTopic;

	@Test
	public void queueMessage() throws InterruptedException {
		Thread.sleep(1000);
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		Thread.sleep(1000);
		assertThat(appender.getFirstMessage()).isEqualTo(
				"UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:user");
	}

	@Test
	public void topicMessage() throws InterruptedException {
		Thread.sleep(1000);
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendTopic(user);
		Thread.sleep(1000);
		assertThat(appender.getFirstMessage()).isEqualTo(
				"UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:user");
	}

	@Test
	public void topicMessageWithWrongType() throws InterruptedException {
		Thread.sleep(1000);
		LogbackListAppender appender = new LogbackListAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		advancedJmsTemplate.send(advancedNotifyTopic, new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {

				MapMessage message = session.createMapMessage();
				message.setStringProperty("objectType", "role");
				return message;
			}
		});

		Thread.sleep(1000);
		assertThat(appender.isEmpty()).isTrue();
	}
}
