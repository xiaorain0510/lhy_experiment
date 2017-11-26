package identifier.dao;

import java.io.Serializable;
import java.util.List;


/**
 * @author lhy
 *
 */
public interface BaseDAO<T,PK> {

	public boolean Save(T entity);
	
	/**
	 * 
	 * @param entity
	 * @return 返回刚保存数据的主键
	 */
	public Serializable save(T entity);
	
	public boolean Delete(T entity);
	
	public boolean Update(T entity);
	
	public List<T> FindAll(Class<T> entityclass);
	
}
