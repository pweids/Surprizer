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
    private String tableName = "surprizer_lists";
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
	   
	public GiftList create(GiftList giftList) throws DAOException {
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
        return newList;
	}
	
    public GiftList lookup(int listId) throws DAOException {
        try {
            GiftList[] results = factory.match(MatchArg.equals("listId", listId));
       
            if (results.length > 1) {
                throw new DAOException("Multiple entries found for: "+ results[0].getListName());
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
            results = factory.match(MatchArg.equals("listId", modifiedList.getListId()));
            if(results.length == 0)
                throw new DAOException("GiftList could not be modified. No list found with id:" + modifiedList.getListId());
            System.out.println("MODIFYING LIST");
            factory.copyInto(modifiedList,results[0]);
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results[0];
    }
    
	public void delete(int listId) throws DAOException {
		try {
			factory.delete(listId);
		} catch (RollbackException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
    public GiftList[] getUserLists(User user) throws DAOException {
    	GiftList[] results = null; 
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
    
    public GiftList[] getAllLists() throws DAOException {
    	GiftList[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match();
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results;
    }
    
    public void modifyUserName(int uId, String newUsername) throws DAOException {
    	GiftList[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("userId", uId));
            for (GiftList l : results){
            	l.setOwner(newUsername);
            	factory.copyInto(l,l);	
            }
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }


}
