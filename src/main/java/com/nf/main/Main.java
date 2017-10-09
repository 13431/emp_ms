package com.nf.main;

import com.nf.emp_ms.entity.Employee;
import com.nf.emp_ms.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory sf = SessionUtil.getSessionFactory();
        Session session = sf.openSession();

        Employee e1 = session.get(Employee.class, 7369L);
        System.out.println(e1.getName());
        System.out.println(e1.getSalary());
        System.out.println(e1.getDepartment().getName());

        session.close();
        sf.close();
    }

}
