package com.example.stacy.refill.DBManager;

import com.example.stacy.refill.ListItem;

import java.util.List;
import java.util.concurrent.Callable;

public class SyncListItemDao implements SyncDao<ListItem>{

    ListItemDao listItemDao;

    public SyncListItemDao(ListItemDao listItemDao) {
        this.listItemDao = listItemDao;
    }

    public List<ListItem> getAll() {
        Callable<List<ListItem>> c = new Callable<List<ListItem>>() {
            public List<ListItem> call() throws Exception {
                return listItemDao.getAll();
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ListItem findByName(final String name) {
        Callable<ListItem> c = new Callable<ListItem>() {
            public ListItem call() throws Exception {
                return listItemDao.findByName(name);
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertAll(final ListItem... listItems) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                listItemDao.insertAll(listItems);
                return 0;
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(final ListItem listItem) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                listItemDao.updateListItem(listItem);
                return 0;
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int delete(final ListItem listItem) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                listItemDao.delete(listItem);
                return 0;
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
