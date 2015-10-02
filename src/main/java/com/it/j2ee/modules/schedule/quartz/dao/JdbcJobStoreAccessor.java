package com.it.j2ee.modules.schedule.quartz.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcJobStoreAccessor {

	private static String query_sql = "select a.job_name,a.job_group,TRIGGER_NAME,TRIGGER_GROUP,NEXT_FIRE_TIME,PREV_FIRE_TIME,PRIORITY,TRIGGER_STATE,TRIGGER_TYPE,START_TIME,END_TIME from qrtz_job_details a left join qrtz_triggers b on a.JOB_NAME=b.JOB_NAME and a.JOB_GROUP=b.JOB_GROUP";

	public static List<Object> query() throws Exception {
		Connection conn = JdbcUtils.getConnection();
		ResultSet rs = JdbcUtils.query(JdbcUtils.getConnection(), query_sql);

		List<Object> list = new ArrayList<Object>();
		while (rs.next()) {
			String job_name = rs.getString("job_name");
			String job_group = rs.getString("job_group");
			String trigger_name = rs.getString("TRIGGER_NAME");
			String trigger_group = rs.getString("TRIGGER_GROUP");
			String next_fire_time = rs.getString("NEXT_FIRE_TIME");
			String prev_fire_time = rs.getString("PREV_FIRE_TIME");
			String priority = rs.getString("PRIORITY");
			String trigger_state = rs.getString("TRIGGER_STATE");
			String trigger_type = rs.getString("TRIGGER_TYPE");
			String start_time = rs.getString("START_TIME");
			String end_time = rs.getString("END_TIME");

			Map<String, String> map = new HashMap<String, String>();
			map.put("job_name", job_name);
			map.put("job_group", job_group);
			map.put("trigger_name", trigger_name);
			map.put("trigger_group", trigger_group);
			map.put("next_fire_time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(next_fire_time))));
			map.put("prev_fire_time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(prev_fire_time))));
			map.put("priority", priority);
			map.put("trigger_state", trigger_state);
			map.put("trigger_type", trigger_type);
			map.put("start_time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(start_time))));
			map.put("end_time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(end_time))));
			list.add(map);
		}

		rs.close();
		conn.close();

		return list;
	}
}
