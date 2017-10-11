package my.tests;

import com.nf.emp_ms.entity.Department;
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


    @Test
    public void testPagination() {
        // 分页
        // oracle: rownum/row_number()
        // mysql: limit offset
        // SQLServer: top [15/45]
        // 在 hibernte 中，对分页的功能进行了封装

        String hql = "from Employee ";
        List<Employee> employees = session.createQuery(hql, Employee.class)
                .setFirstResult(8)        // 从哪一行开始，如果 0 代表从 第一行开始
                .setMaxResults(5).list(); // 取多少行数据

        System.out.println(employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testResultCount () {
        // 结果集的数目
        long a = session.createQuery(
                "select count(*) from Employee e where e.department.name='SALES'", Long.class)
                .uniqueResult();

        // Query 查询的隐式关联查询(JOIN)
        // select * from emp left join dept using(deptno) where dept.ename = 'SALES';

        long b = session.createQuery(
                "select count(*) from Employee e where e.department.deptno=30", Long.class)
                .iterate().next();

        System.out.println(a);
        System.out.println(b);
    }


    @Test
    public void testJoinQueryPre () {
        // 默认情况下， Entity 属性，采取的是 EAGER + JOIN 的策略
        Employee employee = session.get(Employee.class, 7521L);
        System.out.println(employee.getDepartment().getLocation());
    }

    @Test
    public void testJoinQuery1 () {
        // 默认情况下，Join 策略对 Query 查询是无效的
        // 默认 Query 查询会采取 Select 的策略
        String hql = "from Employee ";
        Employee employee = session.createQuery(hql, Employee.class).list().get(0);
        System.out.println(employee.getName() + "/" + employee.getDepartment().getLocation());
    }

    @Test
    public void testJoinQuery2 () {
        // 1. 隐式的 Join 调用
        String hql = "from Employee e where e.department.location='NEW YORK'";
        Object employee = session.createQuery(hql).list().get(0);
        System.out.println(employee);
    }

    @Test
    public void testJoinQuery3 () {
        // 2. 显式的 Join 调用
        // 默认情况下，返回一个数组，数组里面，包含着连接的各个实体类对象
        // [employee, department]
        String hql = "from Employee e left join e.department where e.id=7521";
        Object[] employees = (Object[]) session.createQuery(hql).list().get(0);
        System.out.println(employees.length);
        System.out.println(">>> 元素分别为:");
        System.out.println(employees[0]);
        System.out.println(employees[1]);

    }

    @Test
    public void testJoinQuery4Fetch () {
        // 2. 显式的 Join 调用
        // 通过 fetch 关键词，设置，返回的结果是 Employee 对象
        String hql = "from Employee e left join fetch e.department where e.id=7521";
        Employee employee = (Employee) session.createQuery(hql).list().get(0);
        System.out.println(employee);

        System.out.println("=============");

        System.out.println(session.get(Employee.class, 7521L));
    }


    // 删除、更新操作
    @Test
    public void testDeleteInitData() {
        Department department = new Department();
        department.setName("保安");
        department.setLocation("校门口");

        Employee employee1 = new Employee();
        employee1.setName("小狗");
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setName("小猫");
        employee2.setDepartment(department);

        Employee employee3 = new Employee();
        employee3.setName("小猪");
        employee3.setDepartment(department);

        session.save(department);
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
    }

    @Test
    public void testDelete2Query () {
        // 在 query 中进行删除
        int o = session.createQuery("delete Employee where id=8011").executeUpdate();
        System.out.println(o);
    }

    @Test
    public void testDelete3 () {
        // 通过 delete 方法删除
        // 关联删除
        Employee employee = session.load(Employee.class, 8009L);
        session.delete(employee);
    }

    @Test
    public void testDelete4 () {
        // Employee employee = session.load(Employee.class, 8009L);
        Employee employee = new Employee();
        employee.setEmpno(8010L);
        session.delete(employee);
    }

    @Test
    public void testDeleteCascade() {

        /*
        @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
        private List<Employee> employees = new ArrayList<>();
        */

        // delete from dept where deptno = 107
        /*
        Department department = new Department();
        department.setDeptno(107L);
        session.delete(department);
        */

// delete Employee e where e.department.deptno = 108;
// delete Department where id=108
        session.createQuery("delete Department where id=108").executeUpdate();

//        session.delete(session.load(Department.class, 107L));
    }

    @Test
    public void testSaveCascade() {
        Department d = new Department();
        d.setName("svt");

        Employee y = new Employee();
        y.setName("tvs");
        y.setDepartment(d);

        session.persist(y);
    }

    @Test
    public void testUpdate1() {
        Employee xiaogou = session.load(Employee.class, 8016L);
        xiaogou.setSalary(1888F);
        session.save(xiaogou);
    }

    @Test
    public void testUpdate2Query() {
        int count = session.createQuery("update Employee set salary = :sal where id = :id")
                .setParameter("sal", 2555F)
                .setParameter("id", 8017L)
                .executeUpdate();
        if (count == 1) {
            System.out.println("修改成功");
        } else {
            System.err.println("修改失败");
        }
    }

}
