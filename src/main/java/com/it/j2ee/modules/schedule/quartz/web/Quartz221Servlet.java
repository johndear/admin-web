package com.it.j2ee.modules.schedule.quartz.web;
//package com.it.j2ee.modules.schedule.quartz.web;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.quartz.CronExpression;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerFactory;
//import org.quartz.TriggerKey;
//import org.quartz.impl.JobDetailImpl;
//import org.quartz.impl.StdSchedulerFactory;
//import org.quartz.impl.triggers.CronTriggerImpl;
//
//import com.it.j2ee.modules.schedule.quartz.dao.JdbcJobStoreAccessor;
//import com.it.j2ee.modules.schedule.quartz.job.CPJob;
//
//public class CopyOfQuartzServlet extends HttpServlet {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 3L;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String flag = request.getParameter("flag");
//		String groupName= request.getParameter("groupName");
//		String jobName = request.getParameter("jobName");
//		String cronExpression = request.getParameter("cronExpression");
//		String triggerPriority = request.getParameter("triggerPriority");
//		String triggerName="trigger_"+jobName;
//		
//		try {
//			// Initiate a Schedule Factory
//			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//			Scheduler scheduler = schedulerFactory.getScheduler();
//			
//			if("1".equals(flag)){
//				Map<String,String> paramsMap=new HashMap<String,String>();
//				paramsMap.put("url", request.getParameter("url"));
//				paramsMap.put("method", request.getParameter("method"));
//				
//				// Initiate JobDetail with job name, job group, and executable job
//				JobDetail jobDetail = new JobDetailImpl(jobName,groupName, CPJob.class);
////				jobDetail.getJobDataMap().put(CPJob.REQUEST_KEY, request);
//				CronTriggerImpl cronTrigger = new CronTriggerImpl(triggerName,groupName);
//				CronExpression exp = new CronExpression(cronExpression);
//				cronTrigger.setPriority(Integer.parseInt(triggerPriority));
//				cronTrigger.setCronExpression(exp);
//				
//				// start the scheduler
//				scheduler.scheduleJob(jobDetail, cronTrigger);
//				scheduler.start();
//			}else if("2".equals(flag)){
//				List<Object> list = JdbcJobStoreAccessor.query();
//				request.setAttribute("list", list);
//				request.getRequestDispatcher("WEB-INF/views/quartz/list.jsp").forward(request, response);
//			}else if("3".equals(flag)){//暂停任务 
//				scheduler.pauseTrigger(new TriggerKey(triggerName, groupName));//停止触发器
//			}else if("4".equals(flag)){//恢复任务
//				scheduler.resumeTrigger(new TriggerKey(triggerName, groupName));//重启触发器  
//			}else if("5".equals(flag)){//移除任务 
//				scheduler.pauseTrigger(new TriggerKey(triggerName, groupName));//停止触发器  
//	            scheduler.unscheduleJob(new TriggerKey(triggerName, groupName));//移除触发器
//			}else{
//				request.getRequestDispatcher("WEB-INF/views/quartz/add.jsp").forward(request, response);
//			}
//			
////			super.doGet(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//
//}
