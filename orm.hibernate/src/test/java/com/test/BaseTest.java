package com.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-content.xml")
public class BaseTest {

	protected Session session;
	private Transaction transaction;
	private @Autowired SessionFactory sessionFactory;

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
	
	@Test
	public void test(){
		System.out.println();
	}

}
