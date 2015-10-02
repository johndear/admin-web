package com.it.j2ee.modules.schedule.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class CPJob implements Job{
	
	public static String message = "";
	
	public static int interval = 16;// 间隔期数，默认16期
	public static boolean lookHistory = false;// 查看之前期数中的出现次数
	private static String default_dataPath= "http://www.lecai.com/lottery/draw/list/566/?d=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	private static String default_savePath = "D:\\data\\hm.txt";
	
	public static String dataPath = default_dataPath;
	public static String savePath = default_savePath;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("[quartz任务调度系统] 执行"+ arg0.getJobDetail().getDescription() +"任务, Time at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}

}
