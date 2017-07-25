package com.thesis.tremor.database.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.thesis.tremor.database.dao.UserDAO;
import com.thesis.tremor.database.entity.User;
import com.thesis.tremor.objects.ObjectList;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Nov 13, 2016
 */
@Repository
public class UserDAOImpl
		extends AbstractDAO<User, Long> 
		implements UserDAO {

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		conjunction.add(Restrictions.eq("username", username));
		conjunction.add(Restrictions.eq("password", password));
		
		return findUniqueResult(null, null, null, conjunction);
	}
	
	@Override
	public ObjectList<User> findAllWithPaging(int pageNumber, int resultsPerPage, String searchKey) {
		return findAllWithPagingAndOrder(pageNumber, resultsPerPage, searchKey, null);
	}
	
	@Override
	public ObjectList<User> findAllWithPagingAndOrder(int pageNumber, int resultsPerPage, String searchKey,
			Order[] orders) {
		final Junction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("isValid", Boolean.TRUE));
		
		if(StringUtils.isNotBlank(searchKey))
		{
			conjunction.add(Restrictions.disjunction()
					.add(Restrictions.ilike("firstName", searchKey, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("lastName", searchKey, MatchMode.ANYWHERE)));
		}
		
		return findAllByCriterion(pageNumber, resultsPerPage, null, null, null, orders, conjunction);
	}
}
