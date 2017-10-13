package my.tests;

import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.junit.Test;

public class GetLoadTest extends BaseTest {

    @Test
    public void getTest () {
        Employee employee = (Employee) session.get("com.nf.emp_ms.entity.Employee", 7521L);
        System.out.println(employee);
    }

    @Test
    public void loadTest () {
        Employee employee = (Employee) session.load(Employee.class, 7521L);
        System.out.println(employee);
    }

}
