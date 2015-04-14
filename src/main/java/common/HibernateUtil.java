package common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();

    static {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            return null;
        }
        Session result = sessionThreadLocal.get();
        if (result == null) {
            result = sessionFactory.openSession();
            sessionThreadLocal.set(result);
        }
        return result;
    }

    public static void closeSession() {
        Session session = sessionThreadLocal.get();
        sessionThreadLocal.set(null);
        if (session != null) {
            session.close();
        }
    }

    public static void main(String[] args) {
        System.out.println(HibernateUtil.getSession().isOpen());
    }
}
