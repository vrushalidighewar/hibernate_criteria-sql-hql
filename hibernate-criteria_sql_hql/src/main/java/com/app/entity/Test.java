package com.app.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.transform.ResultTransformer;

public class Test {
	
	public void criteriaTest() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		List<Employee> list=cr.list();
		list.forEach(System.out::println);
	}

	public void andRestriction() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		cr.add(Restrictions.eq("name" , "B")).add(Restrictions.eq("mobile", "321456"));
		List<Employee> list=cr.list();
		list.forEach(System.out::println);
	}
	
	public void betweenRestriction() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		//cr.add(Restrictions.between("salary", new Double(15000d), new Double(20000d)));
		cr.add(Restrictions.lt("salary", new Double(23000d)));
		List<Employee> list=cr.list();
		list.forEach(System.out::println);
	}

	public void uniqueResultRestriction() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		cr.add(Restrictions.eq("name", "B")).add(Restrictions.eq("mobile", "321456"));
		Employee employee=(Employee) cr.uniqueResult();
		System.out.println(employee);
		
	}
	
	public void projectionTest() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		cr.setProjection(Projections.min("salary"));
		Double count=(Double) cr.uniqueResult();
		System.out.println();
	}
	
	public void columnwiseProjection() {
		Session session=HibernateUtility.getSession().openSession();
		Criteria cr=session.createCriteria(Employee.class);
		ProjectionList projectionList=Projections.projectionList();
		projectionList.add(Projections.property("name"));
		projectionList.add(Projections.property("mobile"));
		
		cr.setProjection(projectionList);
		
		cr.setResultTransformer(new ResultTransformer(){

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Employee employee=new Employee();
				employee.setName((String) tuple[0]);
				employee.setMobile((String) tuple[1]);
				return employee;
			}

			@Override
			public List transformList(List list) {
				return list;
			}
		});
		List<Employee> list=cr.list();
		list.forEach(System.out::println);
	}
	
	public void hql() {
		Session session=HibernateUtility.getSession().openSession();
		Query query=session.createQuery("from Employee");
		query.setResultTransformer(new ResultTransformer() {

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Employee employee=new Employee();
						employee.setId((Integer) tuple[0]);
				        employee.setName((String) tuple[1]);
				        employee.setMobile((String) tuple[2]);
				        employee.setSalary((Double) tuple[3]);
				        employee.setAge((Integer) tuple[4]);
				
				return employee;
			}

			@Override
			public List transformList(List list) {
				
				return list;
			}
		});
		query.list().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		
		//t.criteriaTest();
		//t.andRestriction();
		//t.betweenRestriction();
		//t.uniqueResultRestriction();
		//t.projectionTest();
		//t.columnwiseProjection();
		t.hql();
	}

}
