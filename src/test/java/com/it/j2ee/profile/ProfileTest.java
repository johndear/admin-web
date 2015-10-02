package com.it.j2ee.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.it.j2ee.spring.Profiles;

/**
 * 读取相应环境的properties属性值，如：dev、production..
 * 生产环境打包时使用命令：mvn package -P production
 * 在本地jetty中运行时添加环境变量：spring.profiles.active = dev（或在web.xml启动时设置为默认参数）
 * 参考Profiles
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@ActiveProfiles(Profiles.DEVELOPMENT)
public class ProfileTest {
	
	@Value("${jdbc.url}")
	public String url;
	
	@Test
	public void getProperties(){
		System.out.println(url);
	}


}
