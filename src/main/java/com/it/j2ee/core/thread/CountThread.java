package com.it.j2ee.core.thread;

/**
 * 当多个线程同时操作一个数据结构的时候产生了相互修改和串行的情况，没有保证数据的一致性，我们通常称之这种设计的代码为”线程不安全的“。
 * 不管多少个用户过来，都保证咱们的数据的高度一致性和准确性就叫线程安全的；
 * 
 * 什么是线程安全性呢？是不是一定要加锁才是线程安全性的呢？
 * 个人感觉只要你代码里面没有变量互串，线程之间互不影响，例如server的设计方法，就是线程安全的，
 * 例如上面五个人干了同一件事情，如果让5个人干5件不一样的事情，或者1个人干5件事情，那也是安全的。
 * 而不安全在java工作中主要针对单例模式的应用而言的，怎么保证一件事情被一群人干完，又快又正确；
 * 想实现线程安全大概有三种方法：

	1：多实例，也就是不用单例模式了。
	
	2：使用java.util.concurrent下面的类库。
	
	3：使用锁机制synchronized,lock方式。
 *
 */
public class CountThread extends Thread {

	private Count count;

	public CountThread(Count count) {

		this.count = count;

	}

	public void run() {

		count.add();

	}

	public static void main(String[] args) {
		for (int k = 0; k < 50; k++) {// 循环50次就能看到，不是预期想要的结果。结果有可能是4，也可能是5
			System.out.println("=====================");
			
			// 方法主体 start
			Count count = new Count();
			
			for (int i = 0; i < 5; i++) {
				
				CountThread task = new CountThread(count);
				
				task.start();
				
			}
			
			try {
				
				Thread.sleep(100l);// 等5个人干完活
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				
			}
			
			System.out.println("5个人干完活:最后的值:" + count.num);
			// 方法主体 end
			
		}
		

	}

}
