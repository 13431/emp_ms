package my.tests;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.junit.Test;

import java.util.List;

/**
 * N + 1
 */
public class Nplus1Test extends BaseTest {
    @Test
    public void testFirst () {
        // 请您，加载编号为 20 的部门

        String hql = "from Department where deptno > 10";

        List<Department> departments = session.createQuery(hql, Department.class)
                .list();
        System.out.println("===");

        for (Department department : departments) {
            for (Employee employee : department.getEmployees()) {
                System.out.printf("部门: %s, 姓名: %s\n",
                        department.getName(),
                        employee.getName());
            }
        }
    }

    @Test
    public void testSecond () {
        String hql = "from Department d left join fetch d.employees where d.deptno > 10";

        List<Department> departments = session.createQuery(hql, Department.class)
                .list();
        System.out.println("===");

        for (Department department : departments) {
            for (Employee employee : department.getEmployees()) {
                System.out.printf("部门: %s, 姓名: %s\n",
                        department.getName(),
                        employee.getName());
            }
        }
    }
}
