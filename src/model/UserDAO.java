package model;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import databeans.User;

public class UserDAO {
    private BeanFactory<User> factory;
    private String tableName = "surprizer_users";
    private String PRIMARY_KEY = "userId";

    public UserDAO()  throws DAOException {
        try {
            BeanTable<User> table = BeanTable.getInstance(User.class, tableName);
            if (!table.exists()) table.create(PRIMARY_KEY);
            
            table.setIdleConnectionCleanup(true);

            factory = table.getFactory();
        } catch (BeanFactoryException e) {
            throw new DAOException(e);
        }
    }
    
	public User create(User user) throws DAOException {
		User newUser = null;
        try {
        	Transaction.begin();
        	User[] results = factory.match(MatchArg.equals("email",user.getEmail()));
        	if(results.length > 0)
    			throw new DAOException("Email Address " + user.getEmail() + " already registered");
        	newUser = factory.create();
        	factory.copyInto(user,newUser);
			
        	Transaction.commit();
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
        return newUser;
	}
	
    public User lookup(String email) throws DAOException {
        try {
            User[] results = factory.match(MatchArg.equals("email",email));
            if (results.length > 1) {
                throw new DAOException("Multiple entries found for email: "+email);
            } else if (results.length == 0) {
                throw new DAOException("No user found with that email");
            }
            return results[0];
        } catch (RollbackException e) {
            throw new DAOException(e);
        }
    }
    
    public User [] lookupByNameOrEmail(String search) throws DAOException {
        try {
            User[] results = factory.match(MatchArg.or(MatchArg.contains("name",search), MatchArg.equals("email", search)));
            if (results.length == 0) {
                throw new DAOException("No user found with that email");
            }
            return results;
        } catch (RollbackException e) {
            throw new DAOException(e);
        }
    }
    
    
    public User modify(User modifiedUser) throws DAOException {
    	User[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("email", modifiedUser.getEmail()));
            if(results.length == 0)
                throw new DAOException("User " + modifiedUser.getEmail() + " not found");
            factory.copyInto(modifiedUser,results[0]);
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results[0];
    }
}
