package model;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import databeans.GiftList;
import databeans.User;
import databeans.UsersLists;

public class UsersListsDAO {
	private BeanFactory<UsersLists> factory;
    private String tableName = "surprizer_userslists";
    private String PRIMARY_KEY = "userListId";
	    
    public UsersListsDAO()  throws DAOException {
        try {
            BeanTable<UsersLists> table = BeanTable.getInstance(UsersLists.class, tableName);
            if (!table.exists()) table.create(PRIMARY_KEY);
            table.setIdleConnectionCleanup(true);
            factory = table.getFactory();
        } catch (BeanFactoryException e) {
            throw new DAOException(e);
        }
    }
	   
	public UsersLists create(UsersLists usersLists) throws DAOException {
		UsersLists newUsersLists = null;
        try {
        	Transaction.begin();
        	UsersLists[] results = factory.match(MatchArg.and(MatchArg.equals("listID", usersLists.getListId()), MatchArg.equals("userId", usersLists.getUserId())));
        	if(results.length > 0)
    			throw new DAOException("Relation already exists");
        	newUsersLists = factory.create();
			factory.copyInto(usersLists,newUsersLists);
			Transaction.commit();
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
        return usersLists;
	}
	
//    public GiftList lookup(int listId) throws DAOException {
//        try {
//            GiftList[] results = factory.match(MatchArg.equals("listId", listId));
//       
//            if (results.length > 1) {
//                throw new DAOException("Multiple entries found for: "+ results[0].getListName());
//            } else if (results.length == 0) {
//                throw new DAOException("GiftList not found");
//            }
//            return results[0];
//        } catch (RollbackException e) {
//            throw new DAOException(e);
//        }
//    }
//
//    public GiftList modify(GiftList modifiedList) throws DAOException {
//    	GiftList[] results = null; 
//    	try {
//            Transaction.begin();
//            results = factory.match(MatchArg.equals("listId", modifiedList.getListId()));
//            if(results.length == 0)
//                throw new DAOException("GiftList could not be modified");
//            System.out.println("MODIFYING LIST");
//            factory.copyInto(modifiedList,results[0]);
//            Transaction.commit();
//        } catch (RollbackException e) {
//            throw new DAOException(e);
//        } finally {
//            if (Transaction.isActive()) Transaction.rollback();
//        }
//        return results[0];
//    }
    
	public void delete(int userId, int listId) throws DAOException {
		UsersLists[] results = null;
		int userListId = 0;
		try {
			Transaction.begin();
            results = factory.match(MatchArg.and(MatchArg.equals("listId", listId), MatchArg.equals("userId", userId)));
            Transaction.commit();
            if(results.length == 0)
              throw new DAOException("GiftList could not be deleted, no list with id:"+listId+".");
            userListId = results[0].getUserListId();
			factory.delete(userListId);
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
      		if (Transaction.isActive()) Transaction.rollback();
  		}
	}
	public void deleteList(int listId) throws DAOException {
		UsersLists[] results = null;
		int userListId = 0;
		try {
			Transaction.begin();
            results = factory.match(MatchArg.equals("listId", listId));
            Transaction.commit();
            for(UsersLists ul : results){
            	factory.delete(ul.getUserListId());	
            }
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
      		if (Transaction.isActive()) Transaction.rollback();
  		}
	}
	
    public UsersLists[] getUserLists(User user) throws DAOException {
    	UsersLists[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("userId", user.getUserId()));
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results;
    }
    
    public boolean isFollowing(User user, GiftList list) throws DAOException {
    	UsersLists[] results = null; 
    	boolean following = false;
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.and(MatchArg.equals("listId",  list.getListId()), MatchArg.equals("userId", user.getUserId())));
            if(results.length != 0) following = true;
            else following = false;
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return following;
    }


}
