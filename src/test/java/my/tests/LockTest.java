package my.tests;

import com.nf.emp_ms.entity.Company;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.LockMode;
import org.junit.Test;

import javax.persistence.LockModeType;
import java.util.List;

public class LockTest extends BaseTest {

    @Test public void testPessimisticLock1() {
        Employee xiaogou = session.load(Employee.class, 8015L, LockMode.UPGRADE_NOWAIT);
        xiaogou.setSalary(5000F);
        session.save(xiaogou);
    }

    @Test public void testPessimisticLock2() {
        Employee xiaogou = session.load(Employee.class, 8015L);
        // ... 若干事务

        session.lock(xiaogou, LockMode.UPGRADE_NOWAIT);
        xiaogou.setSalary(5000F);
        session.save(xiaogou);
    }

    @Test public void testPessimisticLock3() {
         List<Employee> employees = session.createQuery("from Employee where id=8015", Employee.class)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .list();

        System.out.println(employees.size());
    }

    @Test public void testOptimisticLock1() {
        session.save(new Company("华为", "深圳"));
        session.save(new Company("格力", "珠海"));
        session.save(new Company("金山", "珠海"));
        session.save(new Company("魅族", "珠海"));
        session.save(new Company("用友", "珠海"));
    }

    @Test public void testOptimisticLock2() {
        // 悲观锁示例
        Company hw = session.load(Company.class, 21L, LockMode.UPGRADE_NOWAIT);
        hw.setAddress("广东省东莞市");
        session.save(hw);
    }

    @Test public void testOptimisticLock3() {
        Company hw = session.load(Company.class, 26L);
        hw.setAddress("广东省东莞市");
        session.save(hw);
    }

    @Test public void testOptimisticLock4() {
        Company hw = session.load(Company.class, 26L);
        hw.setAddress("广东省佛山市");
        session.save(hw);
    }

}
