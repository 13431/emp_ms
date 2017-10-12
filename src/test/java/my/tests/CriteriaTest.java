package my.tests;

import com.nf.emp_ms.entity.Department;
import com.nf.emp_ms.entity.Employee;
import my.BaseTest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class CriteriaTest extends BaseTest {

    @Test
    public void testFirst() {
        Criteria criteria = session.createCriteria(Employee.class);
        List list = criteria.list();
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testFirst2() {
        // 链式语句
        for (Object o : session.createCriteria(Employee.class).list()) {
            System.out.println(o);
        }
    }


    @Test
    public void testFirst3() {
        Object o = session.createCriteria(Employee.class)
                .add(Restrictions.eq("name", "KING"))
                .add(Restrictions.eq("empno", 7839L))
                .uniqueResult();
        System.out.println(o);
    }

    @Test
    public void testCondition() {
        List<Employee> employees = session.createCriteria(Employee.class)
                .add(Restrictions.like("name", "J%"))
                .add(Restrictions.ge("salary", 2000F))  // gt: great than  ge: great equal  lt: less than le: less equal
                .list();

        for (Employee employee : employees) {
            System.out.println(employee.getName() + " : " + employee.getDepartment().getName());
        }
    }

    @Test
    public void testConditionMore() {
        // 查询所有工资大于 3000 的人和有提成的人
        List<Employee> employees = session.createCriteria(Employee.class)
                .add(Restrictions.or(
                        Restrictions.gt("salary", 3000F),
                        Restrictions.isNotNull("commission")
                ))
                .add(Restrictions.lt("hireDate", new Date()))
                .list();

        for (Employee employee : employees) {
            System.out.printf("> %s, %.1f, %.1f\n", employee.getName(), employee.getSalary(), employee.getCommission());
        }
    }

    @Test
    public void testOrderBy() {
        // 增加排序
        List<Employee> employees = session.createCriteria(Employee.class)
                .add(Restrictions.or(
                        Restrictions.gt("salary", 3000F),
                        Restrictions.isNotNull("commission")
                ))
                .add(Restrictions.lt("hireDate", new Date()))
                .addOrder(Order.asc("salary"))
                .addOrder(Order.asc("commission"))
                .list();

        for (Employee employee : employees) {
            System.out.printf("> %s, %.1f, %.1f\n", employee.getName(), employee.getSalary(), employee.getCommission());
        }
    }

    @Test
    public void testProjection() {
        // 投影查询：查询指定字段

        // 指定查询详情
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.property("department"))
                .add(Projections.sum("salary"), "sum")
                .add(Projections.avg("salary"))
                .add(Projections.groupProperty("department"));

        List<Object[]> list = session.createCriteria(Employee.class)
                .setProjection(projectionList)
                .addOrder(Order.desc("sum"))
                .list();

        for (Object[] o : list) {
            System.out.printf("部门: %-12s 总和: %-8.0f 平均: %.0f\n", ((Department) o[0]).getName(), o[1], o[2]);
        }
    }

}
