package com.example.stacy.refill;

import java.util.List;
import java.util.concurrent.Callable;

public class SyncProductDao {

    ProductDao productDao;

    SyncProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    List<Product> getAll() {
        Callable<List<Product>> c = new Callable<List<Product>>() {
            public List<Product> call() throws Exception {
                return productDao.getAll();
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Product> loadAllByIds(final int[] productIds) {
        Callable<List<Product>> c = new Callable<List<Product>>() {
            public List<Product> call() throws Exception {
                return productDao.loadAllByIds(productIds);
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Product findByName(final  String name) {
        Callable<Product> c = new Callable<Product>() {
            public Product call() throws Exception {
                return productDao.findByName(name);
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Product> getOrderedProducts(final int amount) {
        Callable<List<Product>> c = new Callable<List<Product>>() {
            public List<Product> call() throws Exception {
                return productDao.getOrderedProducts(amount);
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void insertAll(final Product... products) {
        Thread myThread = new Thread(new Runnable(){
            public void run(){
                productDao.insertAll(products);
            }
        });
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void updateProduct(final Product product) {
        Thread myThread = new Thread(new Runnable(){
            public void run(){
                productDao.updateProduct(product);
            }
        });
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void delete(final Product product) {
        Thread myThread = new Thread(new Runnable(){
            public void run(){
                productDao.delete(product);
            }
        });
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
