package com.it.j2ee.core.thread;

/**
 * 取钱
 * @author Administrator
 *
 */
public class BankEmployee implements Runnable {
	
	private BankAccountDemo account;

	public BankEmployee(BankAccountDemo account) {
		this.account = account;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			account.subtractAmount(1000);
		}
	}
}
