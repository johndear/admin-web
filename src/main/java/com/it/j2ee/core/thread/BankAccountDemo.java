package com.it.j2ee.core.thread;

/**
 * 基本线程同步（二）同步方法
 * 参考：http://ifeve.com/basic-thread-synchroinzation-2/
 * @author Administrator
 * 
 * 同步方法

	在这个指南中，我们将学习在Java中如何使用一个最基本的同步方法，即使用 synchronized关键字来控制并发访问方法。只有一个执行线程将会访问一个对象中被synchronized关键字声明的方法。如果另一个线程试图访问同一个对象中任何被synchronized关键字声明的方法，它将被暂停，直到第一个线程结束方法的执行。
	
	换句话说，每个方法声明为synchronized关键字是一个临界区，Java只允许一个对象执行其中的一个临界区。
	
	静态方法有不同的行为。只有一个执行线程访问被synchronized关键字声明的静态方法，但另一个线程可以访问该类的一个对象中的其他非静态的方法。 你必须非常小心这一点，因为两个线程可以访问两个不同的同步方法，如果其中一个是静态的而另一个不是。如果这两种方法改变相同的数据,你将会有数据不一致 的错误。
	
	为了学习这个概念，我们将实现一个有两个线程访问共同对象的示例。我们将有一个银行帐户和两个线程：其中一个线程将钱转移到帐户而另一个线程将从账户中扣款。在没有同步方法，我们可能得到不正确的结果。同步机制保证了账户的正确。
	
	只有一个线程能访问一个对象的声明为synchronized关键字的方法。如果一个线程A正在执行一个 synchronized方法，而线程B想要执行同个实例对象的synchronized方法，它将阻塞，直到线程A执行完。
	
	但是如果线程B访问相同类的不同实例对象，它们都不会被阻塞。
	
	不止这些…

	synchronized关键字不利于应用程序的性能，所以你必须仅在修改共享数据的并发环境下的方法上使用它。如果你有多个线程正在调用一个synchronized方法，在同一时刻只有一个线程执行它，而其他的线程将会等 待。如果这个操作没有使用synchronized关键字，所有线程可以在同一时刻执行这个操作，减少总的执行时间。如果你知道一个方法将不会被多个线程 调用，请不要使用synchronized关键字。
	
	你可以使用递归调用synchronized方法。当线程访问一个对象的synchronized方法，你可以调用该对象的其他synchronized方法，包括正在执行的方法。它将不会再次访问synchronized方法。
	
	我 们可以使用synchronized关键字来保护访问的代码块，替换在整个方法上使用synchronized关键字。我们应该使用 synchronized关键字以这样的方式来保护访问的共享数据，其余的操作留出此代码块，这将会获得更好的应用程序性能。这个目标就是让临界区（在同 一时刻可以被多个线程访问的代码块）尽可能短。我们已经使用了synchronized关键字来保护访问指令，将不使用共享数据的长操作留出此代码块。当 你以这个方式使用synchronized关键字，你必须通过一个对象引用作为参数。只有一个线程可以访问那个对象的synchronized代码（代码 块或方法）。通常，我们将使用this关键字引用执行该方法的对象。
		
		synchronized (this) {
			// Java code
		}

	
 *  
 */
public class BankAccountDemo {
	
	private double balance;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public synchronized void addAmount(double amount) {
		double tmp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tmp += amount;
		balance = tmp;
	}

	public synchronized void subtractAmount(double amount) {
		double tmp = balance;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tmp -= amount;
		balance = tmp;
	}
	
	public static void main(String[] args) {
		BankAccountDemo account = new BankAccountDemo();
		account.setBalance(1000);

		// 公司存钱
		BankCompany company = new BankCompany(account);
		Thread companyThread = new Thread(company);
		// 员工取钱
		BankEmployee bank = new BankEmployee(account);
		Thread employeeThread = new Thread(bank);

		System.out.printf("BankAccount : Initial Balance: %f\n", account.getBalance());

		companyThread.start();
		employeeThread.start();

		try {
			// 使用join()方法,等待两个线程结束
			companyThread.join();
			employeeThread.join();
			System.out.printf("BankAccount : Final Balance: %f\n", account.getBalance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
