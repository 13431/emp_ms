package com.nf.emp_ms.dao;

import com.nf.emp_ms.entity.Employee;
import com.nf.emp_ms.util.SessionUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

public class EmpDAO {

    // 通过 id 获取
    public Employee getById(long empno) {
        Session session = SessionUtil.getSession();
        Employee employee = session.get(Employee.class, empno);

        // 立刻获取 Manager
        Hibernate.initialize(employee.getManager());

        session.close();

        return employee;
    }

    // 获取所有员工
    public List<Employee> getAll() {
        Session session = SessionUtil.getSession();

        String hql = "from Employee ";
        List<Employee> employees = session
                .createQuery(hql, Employee.class)
                .list();

        session.close();
        return employees;
    }


    public List<Employee> queryByEname(String ename) {
        Session session = SessionUtil.getSession();

        String hql = "from Employee where name like :name";
        List<Employee> employees = session.createQuery(hql, Employee.class)
                .setParameter("name", (ename + "%").toUpperCase())
                .list();

        session.close();
        return employees;
    }

    public List<Employee> queryByConditions(String ename2, String lowsal, String hisal) {
        Session session = SessionUtil.getSession();
        String hql = "from Employee where 1=1 ";

        if(notempty(ename2)) {
            hql += "and name like '" + ename2 + "%'";
        }

        if(notempty(lowsal)) {
            hql += " and salary >= :sal";
        }

        if(notempty(hisal)) {
            hql += " and salary <= " + hisal;
        }

        hql += " order by salary desc";

        Query<Employee> query = session.createQuery(hql, Employee.class);

        if (notempty(lowsal)) {
            query.setParameter("sal", lowsal);
        }

        List<Employee> employees = query.list();

        session.close();
        return employees;
    }

    public List<Employee> criteriaByConditions(String ename2, String lowsal, String hisal) {
        List<Employee> employees;

        Session session = SessionUtil.getSession();

        Criteria criteria = session.createCriteria(Employee.class);

        if(notempty(ename2))
            criteria.add(Restrictions.like("name", ename2.toUpperCase() + "%"));

        if(notempty(lowsal))
            criteria.add(Restrictions.ge("salary", Float.parseFloat(lowsal)));

        if(notempty(hisal))
            criteria.add(Restrictions.le("salary", Float.parseFloat(hisal)));

        employees = criteria.addOrder(Order.desc("salary")).list();

        session.close();
        return employees;
    }

    public boolean notempty (String input) {
        return input != null && !input.isEmpty();
    }

}
