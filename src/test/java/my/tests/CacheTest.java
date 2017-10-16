package my.tests;

import my.BaseTest;
import org.junit.Test;

public class CacheTest extends BaseTest {

    @Test
    public void testFirst1() {
        session.createQuery("from Employee where id=7782").list();
        session.createQuery("from Employee where id=7782").list();
        session.createQuery("from Employee where id=7782").list();

        session.createQuery("from Employee where id=7782").list();
        session.createQuery("from Employee where id=7782").list();
    }

    @Test
    public void testFirst2() {
        session.createQuery("from Employee where id=7782").setCacheable(true).list();
        session.createQuery("from Employee where id=7782").setCacheable(true).list();
        session.createQuery("from Employee where id=7782").setCacheable(true).list();
        session.createQuery("from Employee where id=7782").setCacheable(true).list();
        session.createQuery("from Employee where id=7782").setCacheable(true).list();
    }

}
