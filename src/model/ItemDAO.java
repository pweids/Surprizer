package model;

import org.mybeans.dao.DAOException;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanFactoryException;
import org.mybeans.factory.BeanTable;
import org.mybeans.factory.MatchArg;
import org.mybeans.factory.RollbackException;
import org.mybeans.factory.Transaction;

import databeans.GiftList;
import databeans.Item;

public class ItemDAO {
	 private BeanFactory<Item> factory;
    private String tableName = "surprizer_items";
    private String PRIMARY_KEY = "itemId";
	    
    public ItemDAO()  throws DAOException {
        try {
            BeanTable<Item> table = BeanTable.getInstance(Item.class, tableName);
            if (!table.exists()) table.create(PRIMARY_KEY);
  
            table.setIdleConnectionCleanup(true);

            factory = table.getFactory();
        } catch (BeanFactoryException e) {
            throw new DAOException(e);
        }
    }
	   
	public Item create(Item item) throws DAOException {
		Item newItem = null;
        try {
        	Transaction.begin();
        	newItem = factory.create();
			factory.copyInto(item,newItem);
			Transaction.commit();
		} catch (RollbackException e) {
			throw new DAOException(e);
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
        return newItem;
	}
	
    public Item lookup(int itemId) throws DAOException {
        try {
            Item[] results = factory.match(MatchArg.equals("itemId", itemId));
            if (results.length == 0) {
                throw new DAOException("Item not found");
            }
            return results[0];
        } catch (RollbackException e) {
            throw new DAOException(e);
        }
    }

    public Item modify(Item modifiedItem) throws DAOException {
    	Item[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("itemId", modifiedItem.getItemId()));
            if(results.length == 0)
                throw new DAOException("Item could not be modified");
            factory.copyInto(modifiedItem,results[0]);
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results[0];
    }
    
	public void delete(int itemId) throws DAOException {
		try {
			factory.delete(itemId);
		} catch (RollbackException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
    public Item[] getListItems(GiftList list) throws DAOException {
    	Item[] results = null; 
    	try {
            Transaction.begin();
            results = factory.match(MatchArg.equals("listId", list.getListId()));
            System.out.println(results.length + " found");
            Transaction.commit();
        } catch (RollbackException e) {
            throw new DAOException(e);
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
        return results;
    }
}
