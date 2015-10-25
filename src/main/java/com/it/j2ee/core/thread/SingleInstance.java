package com.it.j2ee.core.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 采用同步的话，可以使用同步代码块和同步方法两种来完成。
 * 提醒一下，当多个线程共享一个资源的时候需要进行同步，但是过多的同步可能导致死锁。
 * 同步synchronized，在”取值-修改-赋值“过程中都可能会出现并发的情况，多线程同时执行的情况
 * 
 * 参考：
 * http://www.cnblogs.com/rollenholt/archive/2011/08/28/2156357.html
 * @author Administrator
 *
 */
public class SingleInstance {
	
	private static SingleInstance instance = null;
	
	private static Object lock = SingleInstance.class;
	
	private SingleInstance(){
	}
	
	public static SingleInstance getInstance(){
//		if(instance== null){
	//		synchronized (lock) {
				if(instance== null){// 线程1、2同时进入方法体
					// 线程1还没执行完，线程2就已经判断实例是否存在，为了避免这种情况的发生，加上锁，只有等锁被释放，才允许下一个线程执行
					instance = new SingleInstance();
				}
				return instance;
	//		}
//		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 创建一个线程池  
		ExecutorService pool = Executors.newFixedThreadPool(1000);  
		 
		for (int i = 0; i < 10000; i++) {  
			Runnable run = new Runnable() {
				public void run() {
					System.out.println(SingleInstance.getInstance());
				}
			};
			
			Future f = pool.submit(run);  
			// 等待子线程结束
			f.get();
		}  
		
		// 关闭线程池  
		pool.shutdown();  
	}

}
