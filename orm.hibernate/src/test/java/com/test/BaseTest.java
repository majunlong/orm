package com.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest {
	
	public Session session;
	public Transaction transaction;
	public @Autowired SessionFactory sessionFactory;

	@Before
	public void init() {
		this.session = this.sessionFactory.openSession();
		this.transaction = this.session.beginTransaction();
	}

	@After
	public void destory() {
		this.transaction.commit();
		this.session.close();
		this.sessionFactory.close();
	}

}
