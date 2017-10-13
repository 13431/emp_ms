package my.tests;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Test;

import java.util.List;


public class NativeSQLTest extends BaseTest {

    @Test
    public void testFirst () {
        // Query query = session.createQuery("");
        // Criteria criteria = session.createCriteria("");

        // String hql = "from Employee where salary > 3000";
        // session.createQuery(hql).list();

        String sql = "select * from emp where sal > 3000";
        NativeQuery nativeQuery = session.createNativeQuery(sql);
        List res = nativeQuery.list();
        for (Object re : res) {
            System.out.println(re);
        }
    }

    @Test
    public void testFirst2 () {
        // 返回结果是一个数组 Object[]
        String sql = "select empno, ename, sal from emp e where sal > 1000 and comm is not null";

        List<Object[]> list = session.createNativeQuery(sql).list();

        for (Object[] o : list) {
            System.out.printf("编号: %s, 名字: %s, 工资: %s\n",
                    o[0], o[1], o[2]);
        }
    }

    @Test
    public void testFirst3 () {
        // 返回 List<Employee>

        String sql = "select * from emp e where sal > 1000 and comm is not null";

        List<Employee> employees = session.createNativeQuery(sql)
                .addEntity(Employee.class)
                .list();

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testFirst4 () {
        // 返回 Object[] 将多个实体类对象封装到 Object[] 内返回
        String sql = "select e.*, d.* from emp e, dept d where e.deptno = d.deptno and sal >= 3000";

        List<Object[]> list = session.createNativeQuery(sql)
                .addEntity(Employee.class)
                .addEntity(Department.class)
                .list();

        for (Object[] o : list) {
            Employee e = (Employee) o[0];
            Department d = (Department) o[1];

            System.out.printf("%s 在 %s 工作\n", e.getName(), d.getLocation());
        }
    }

    @Test
    public void testFirst5 () {
        // 返回 Object[] 将多个实体类对象封装到 Object[] 内返回
        String sql = "select e.*, d.* from emp e, dept d where e.deptno = d.deptno and sal >= 3000";

        List<Object[]> list = session.createNativeQuery(sql)
                .addEntity("e", Employee.class)
                .addEntity("d", Department.class)
                .list();

        for (Object[] o : list) {
            Employee e = (Employee) o[0];
            Department d = (Department) o[1];

            System.out.printf("%s 在 %s 工作\n", e.getName(), d.getLocation());
        }
    }

    @Test
    public void testParameter() {
        String sql = "select * from emp where sal > :s";
        List<Employee> employees = session.createNativeQuery(sql)
                .setParameter("s", 2000)
                .addEntity(Employee.class)
                .list();

        for (Employee employee : employees) {
            System.out.println(employee.getName());
        }
    }

    @Test
    public void testScalar () {
        // addScalar
        // 1. 指定返回的字段，比如，下面例子，Object[] 中只包含 ename, sal，虽然查询的是 *
        // 2. 指定类型，提高查询效率
        String sql = "select * from emp where deptno > :d";
        List<Object[]> list = session.createNativeQuery(sql)
                .setParameter("d", 30)
                .addScalar("ename", StandardBasicTypes.STRING)
                .addScalar("sal", StandardBasicTypes.FLOAT)
                .list();
        for (Object[] o : list) {
            System.out.println(o[0]);
            System.out.println(o[1]);
            System.out.println("=========");
        }
    }

}
