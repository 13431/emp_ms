package com.nf.emp_ms.dao;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.util.SessionUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class DeptDAO {
    // 通过 id 获取
    public Department getById(long empno) {
        Session session = SessionUtil.getSession();
        Department department = session.get(Department.class, empno);

        // 强制加载
        Hibernate.initialize(department.getEmployees());

        session.close();
        return department;
    }
}
