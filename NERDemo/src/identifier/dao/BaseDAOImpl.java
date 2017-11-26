package identifier.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lhy
 * 
 */
public class BaseDAOImpl<T, PK extends Serializable> implements BaseDAO<T, PK> {

	static Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);
	public SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean Delete(T entity) {
		// TODO Auto-generated method stub
		try {
			this.sessionFactory.getCurrentSession().delete(entity);
			this.sessionFactory.getCurrentSession().flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> FindAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		try {
			String hql = "from " + entityClass.getName();
			Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
			return (List<T>) (q.list() != null && q.list().size() > 0 ? q
					.list() : null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public boolean Save(T entity) {
		// TODO Auto-generated method stub
		try {
			this.sessionFactory.getCurrentSession().save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean Update(T entity) {
		// TODO Auto-generated method stub
		try {
			this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
			this.sessionFactory.getCurrentSession().flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Serializable save(T entity) {
		return this.sessionFactory.getCurrentSession().save(entity);
	}

	}
