package com.nf.emp_ms.dao;

import com.nf.emp_ms.entity.Employee;
import com.nf.emp_ms.util.SessionUtil;
import org.hibernate.Session;

import java.util.List;

public class EmpDAO {

    // 通过 id 获取
    public Employee getById(long empno) {
        Session session = SessionUtil.getSession();
        Employee employee = session.get(Employee.class, empno);
        session.close();

        return employee;
    }

    // 获取所有员工
    public List<Employee> getAll() {
        Session session = SessionUtil.getSession();
        List<Employee> employees = session.createQuery("from Employee ").list();
        session.close();
        return employees;
    }
}
