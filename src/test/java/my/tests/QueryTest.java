package my.tests;

import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryTest extends BaseTest{

    @Test
    public void testBasicResult () {
        // sql
        // hibernate sql: hql
        String hql = "FRom Employee where id = 7521L";
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
    public void testChainSyntax () {
        // 可以采取链式语法
        System.out.println(session.createQuery("from Employee ").list().size());
    }

    @Test
    public void testWhere1 () {
        // 条件中的 name/salary 应该是 Employee 类的属性
        String hql = "from Employee where name like 'M%' and salary >= 1300";
        Iterator iterate = session.createQuery(hql).iterate();
        while (iterate.hasNext()) {
            System.out.println(iterate.next());
        }
    }

    @Test
    public void testWhere2 () {
        // 可以使用占位符
        String hql = "from Employee where name like ? and salary >= ?";
        Iterator iterate = session.createQuery(hql)
                .setString(0, "J%")
                .setParameter(1, 2000F)
                .iterate();

        while (iterate.hasNext()) {
            System.out.println(iterate.next());
        }
    }

    @Test
    public void testWhere3 () {
        // 可以使用占位符，命名占位符
        String hql = "from Employee where name like :name and salary >= :sal";
        List<Employee> employees = session.createQuery(hql)
                .setParameter("sal", 2000F)
                .setParameter("name", "J%")
                .list();

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelect1 () {
        // 默认情况下，如果不写 select 相当于下面语句
        // 会将结果封装到 Employee 的类中
        String hql = "select e from Employee e where name = 'KING'";
        System.out.println(session.createQuery(hql).uniqueResult());
    }

    @Test
    public void testSelect2 () {
        // 得到一个单独的值
        String hql = "select name from Employee where id=:id";

        String name = (String) session.createQuery(hql)
                .setParameter("id", 7521L)
                .uniqueResult();

        System.out.println(name);
    }

    @Test
    public void testSelect3Single () {
        // 得到一个单独的值，封装到 list 中
        String hql = "select name from Employee";
        List<String> names = session.createQuery(hql).list();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testSelect4Array () {
        // 如果查询多个条件，得到的是一个 Object[] 数组
        String hql = "select name, salary from Employee where id=7521";
        Object[] os = (Object[]) session.createQuery(hql).uniqueResult();
        System.out.println(os[0] + " ///// " + os[1]);
    }

    @Test
    public void testSelect5 () {
        // 聚合函数，返回 Object[] 数组
        String hql = "select max(salary), avg (salary), sum (salary) from Employee ";
        Object[] os = (Object[]) session.createQuery(hql).uniqueResult();
        System.out.printf("所有人的最大工资 %f, 平均工资是 %f, 工资总和是 %f\n",
                os[0], os[1], os[2]);
    }

    @Test
    public void testSelect6Group () {
        // 聚合函数 group by，返回 Object[] 数组
        String hql = "select e.department.deptno, max(salary), avg (salary), sum (salary) from Employee e group by e.department order by e.department.deptno";
        List<Object[]> oslist = session.createQuery(hql).list();
        for (Object[] os : oslist) {
            System.out.printf("组 %d 所有人的最大工资 %.2f, 平均工资是 %.2f, 工资总和是 %.2f\n",
                     os[0], os[1], os[2], os[3]);
        }
    }

    @Test
    public void testSelect7List () {
        // 将返回结果封装到 list 中
        String hql = "select new list(name, salary) from Employee where id=7521";
        List e = (List) session.createQuery(hql).uniqueResult();

        System.out.println(e.size());
        System.out.println(e.get(0));
        System.out.println(e.get(1));
    }

    @Test
    public void testSelect8Map () {
        // 将返回结果封装到 MAP 中
        String hql = "select new map (name as 名字, salary + nvl(commission, 0) as 工资) from Employee where id=7369";
        Map e = (Map) session.createQuery(hql).uniqueResult();

        System.out.println(e);
        System.out.println(e.get("名字") + " 的工资是 " + e.get("工资"));
    }

    @Test
    public void testSelect9 () {
        // 将结果集封装到一个对象中
        String hql1 = "select name, salary from Employee where id=7521";
        Object[] os = (Object[]) session.createQuery(hql1).uniqueResult();
        Boy boy1 = new Boy((String)os[0], (float)os[1]);
        System.out.println(boy1);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");

        String hql = "select new my.tests.Boy(name, salary) from Employee where id=7521";
        Boy boy = session.createQuery(hql, Boy.class).uniqueResult();
        System.out.println(boy);
    }

}
