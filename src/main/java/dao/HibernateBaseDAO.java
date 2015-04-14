package dao;

import org.hibernate.Query;
import org.hibernate.Session;

import common.HibernateUtil;

import java.util.List;


public class HibernateBaseDAO {
    public Session getSession() {
        return HibernateUtil.getSession();
    }

    public void beginThransaction() {
        this.getSession().beginTransaction();
    }

    public void commitThransaction() {
        this.getSession().getTransaction().commit();
    }

    public void rollbackThransaction() {
        this.getSession().getTransaction().rollback();
    }

    public List<?> query(String hql) {
    	this.getSession().clear();
        Query qry = this.getSession().createQuery(hql);
        return qry.list();

    }
}
