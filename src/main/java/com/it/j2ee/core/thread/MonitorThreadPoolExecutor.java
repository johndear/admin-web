package com.it.j2ee.core.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 《 Java并发编程从入门到精通》9.1节:Java线程池的监控
 * @author Administrator
 *
 */
public class MonitorThreadPoolExecutor extends ThreadPoolExecutor {

	public MonitorThreadPoolExecutor(int arg0, int arg1, long arg2,
			TimeUnit arg3, BlockingQueue<Runnable> arg4) {

		super(arg0, arg1, arg2, arg3, arg4);

	}

	protected void beforeExecute(Thread paramThread, Runnable paramRunnable) {

		System.out.println("work_task before:" + paramThread.getName());

	}

	protected void afterExecute(Runnable r, Throwable t) {

		super.afterExecute(r, t);

		System.out.println("work_task after worker thread is :" + r);

	}

	protected void terminated() {

		System.out.println("terminated getCorePoolSize:"
				+ this.getCorePoolSize() + "；getPoolSize:" + this.getPoolSize()
				+ "；getTaskCount:" + this.getTaskCount()
				+ "；getCompletedTaskCount:"

				+ this.getCompletedTaskCount() + "；getLargestPoolSize:"
				+ this.getLargestPoolSize() + "；getActiveCount:"
				+ this.getActiveCount());

		System.out.println("ThreadPoolExecutor terminated:");

	}
}
