package com.it.j2ee.core.thread;

/**
 * 存钱
 * @author Administrator
 *
 */
public class BankCompany implements Runnable {
	
	private BankAccountDemo account;

	public BankCompany(BankAccountDemo account) {
		this.account = account;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			account.addAmount(1000);
		}
	}

}
