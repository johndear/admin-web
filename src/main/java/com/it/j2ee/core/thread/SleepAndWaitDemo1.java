package com.it.j2ee.core.thread;

/**
 * Thread.sleep()与Object.wait（）方法的区别
 * 
 * wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用 
 * obj.wait释放对象锁，使obj对象进入等待集合中并等待唤醒 
 * @author Administrator
 *
 */
public class SleepAndWaitDemo1 implements Runnable {
	int number = 10;

	public void firstMethod() throws Exception {
		System.out.println("first");
		System.out.println(Thread.currentThread());
		System.out.println(this);
		synchronized (this) {
			number += 100;
			System.out.println(number);
			notify();
		}
	}

	public void secondMethod() throws Exception {
		System.out.println("second");
		System.out.println(Thread.currentThread());
		System.out.println(this);
		synchronized (this) {
			/*
			 * sleep()睡眠时，保持对象锁，仍然占有该锁； 而wait()睡眠时，释放对象锁。 因此：
			 * (1)sleep不会访问其他同步代码块 
			 * (2)wait 则会访问其他同步代码块 
			 * (休息2S,阻塞线程)
			 * 以验证当前线程对象的锁被占用时, 是否可以访问其他同步代码块
			 */
//			Thread.sleep(2000);
			this.wait();// 只能在同步代码块中调用wait方法
			System.out.println("Before: " + number);
			number *= 200;
			System.out.println("After: " + number);

		}
	}

//	@Override
	public void run() {
		try {
			firstMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		SleepAndWaitDemo1 threadTest = new SleepAndWaitDemo1();
		Thread thread = new Thread(threadTest);
		System.out.println(Thread.currentThread());
		//thread.run(); // 不会创建新线程，直接调用run方法
		thread.start();// 开始执行该线程（创建一个新线程），由Java虚拟机调用该线程的run方法
//		Thread.sleep(1000);// 主线程sleep，不能访问同步块
		threadTest.secondMethod();// 因为在主线程中调用方法，所以调用的普通方法secondMethod()）会先被执行
	}
}
