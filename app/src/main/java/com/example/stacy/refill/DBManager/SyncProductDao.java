package com.example.stacy.refill.DBManager;

import android.util.Log;

import com.example.stacy.refill.Product;

import java.util.List;
import java.util.concurrent.Callable;

public class SyncProductDao {

    ProductDao productDao;

    public SyncProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
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

    public List<Product> loadAllByIds(final int[] productIds) {
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

    public Product findByName(final String name) {
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

    public List<Product> getOrderedProducts(final int amount) {
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

    public int insertAll(final Product... products) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                Log.i("productDao", products.toString());
                try {
                    productDao.insertAll(products);
                }catch (Exception e){
                    Log.i("daoError",e.getMessage());
                    e.printStackTrace();
                }
                System.out.println(productDao.getAll());
                return 0;
            }
        };
        try {
            return c.call();
        } catch (Exception e) {
            Log.i("daoError",e.getMessage());
            e.printStackTrace();

        }
        return -1;
    }

    public int updateProduct(final Product product) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                productDao.updateProduct(product);
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

    public int delete(final Product product) {
        Callable<Integer> c = new Callable<Integer>() {
            public Integer call() throws Exception {
                productDao.delete(product);
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
