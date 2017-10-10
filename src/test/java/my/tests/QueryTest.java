package my.tests;

import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class QueryTest extends BaseTest{

    @Test
    public void testBasicResult () {
        // sql
        // hibernate sql: hql
        String hql = "FRom Employee where id=7521L";
        Query query = session.createQuery(hql);

        // 第一种得到结果的方法
        List<Employee> employees = query.list();
        System.out.println(employees);

        // 第二种得到结果的方法，这是上面方法的一种快捷写法
        Employee employee = (Employee) query.uniqueResult();
        System.out.println(employee);

        // 第三种得到结果的方法
        Employee employee1 = (Employee) query.iterate().next();
        System.out.println(employee1);
    }

    @Test
    public void testSimpleCount () {
        // 可以采取链式语法
        System.out.println(session.createQuery("from Employee ").list().size());
    }

    @Test
    public void testWhere1 () {
        String hql = "from Employee where name like 'M%' and salary >= 1300";
        Iterator iterate = session.createQuery(hql).iterate();
        while (iterate.hasNext()) {
            System.out.println(iterate.next());
        }
    }

    @Test
    public void testWhere2 () {
        String hql = "from Employee where name like ? and salary >= ?";
        Iterator iterate = session.createQuery(hql)
                .setString(0, "J%")
                .setParameter(1, 2000F)
                .iterate();

        while (iterate.hasNext()) {
            System.out.println(iterate.next());
        }
    }




}
