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

public class GiftListDAO {
	 private BeanFactory<GiftList> factory;
    private String tableName = "suprizer_lists";
    private String PRIMARY_KEY = "listId";
	    
    public GiftListDAO()  throws DAOException {
        try {
            BeanTable<GiftList> table = BeanTable.getInstance(GiftList.class, tableName);
            if (!table.exists()) table.create(PRIMARY_KEY);
  
            table.setIdleConnectionCleanup(true);

            factory = table.getFactory();
        } catch (BeanFactoryException e) {
            throw new DAOException(e);
        }
    }
	   
	public int create(GiftList giftList) throws DAOException {
		GiftList newList = null;
        try {
        	Transaction.begin();
        	GiftList[] results = factory.match(MatchArg.and(MatchArg.equals("listName", giftList.getListName()), MatchArg.equals("owner", giftList.getOwner())));
        	if(results.length > 0)
    			throw new DAOException(giftList.getOwner() + " already has a list named " + giftList.getListName());
        	newList = factory.create();
			factory.copyInto(giftList,newList);
			Transaction.commit();
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
        return newList.getListId();
	}
	
    public GiftList lookup(String listName, int owner) throws DAOException {
        try {
            GiftList[] results = factory.match(MatchArg.and(MatchArg.equals("listName", listName), MatchArg.equals("owner", owner)));
       
            if (results.length > 1) {
                throw new DAOException("Multiple entries found for: "+ listName);
            } else if (results.length == 0) {
                throw new DAOException("GiftList not found");
            }
            return results[0];
        } catch (RollbackException e) {
            throw new DAOException(e);
        }
    }

    public GiftList modify(GiftList modifiedList) throws DAOException {
    	GiftList[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.and(MatchArg.equals("listName", modifiedList.getListName()), MatchArg.equals("owner", modifiedList.getOwner())));
            if(results.length == 0)
                throw new DAOException("GiftList " + modifiedList.getListName() + " not found");
            factory.copyInto(modifiedList,results[0]);
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results[0];
    }
    public GiftList[] getUserLists(User user) throws DAOException {
    	GiftList[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("owner", user.getName()));
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results;
    }
}
