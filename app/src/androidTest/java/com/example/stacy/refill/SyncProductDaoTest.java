package com.example.stacy.refill;

import static org.hamcrest.CoreMatchers.is;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SyncProductDaoTest {
    private ProductDao productDao;
    private AppDatabase appDB;
    private SyncProductDao syncProductDao;

    @Before
    public void createDb() {
        Context context = getTargetContext().getApplicationContext();
        appDB = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        productDao = appDB.productDao();
        syncProductDao = new SyncProductDao(productDao);
    }

    @After
    public void closeDb() throws IOException {
        appDB.close();
    }

    @Test
    public void saveData() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);


        Product milk = new Product("Milk", 1);
        syncProductDao.insertAll(milk);
        List<Product> products = syncProductDao.getAll();

        assertEquals(2, products.size());
        assertEquals(products.get(0).getName(), lime.getName());
        assertEquals(products.get(1).getName(), milk.getName());
    }

    @Test
    public void addMockData() throws Exception {
        syncProductDao.insertAll(
                new Product("Lime", 3),
                new Product("Milk", 3.7),
                new Product("Bread", 1),
                new Product("Coffee", 5.6),
                new Product("Salt", 8),
                new Product("Water", 2.3),
                new Product("Tea", 1.7));
    }

    @Test
    public void findByName() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);

        Product byName = syncProductDao.findByName("Lime");
        assertEquals(lime.getCurrentQuantity(), byName.getCurrentQuantity(), 0);
        assertEquals(lime.getName(), byName.getName());
        assertEquals(lime.getDailyUpdate(), byName.getDailyUpdate(), 0);
        assertEquals(lime.getLastUpdate(), byName.getLastUpdate());
        assertEquals(1, byName.getId());
    }

    @Test
    public void loadByIds() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);

        Product milk = new Product("Milk", 1);
        syncProductDao.insertAll(milk);
        List<Product> products = syncProductDao.getAll();
        int[] ids = {1, 2};
        List<Product> productsByIds = syncProductDao.loadAllByIds(ids);
        assertEquals(products.size(), productsByIds.size());
    }

    @Test
    public void getOrdered() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);
        Product milk = new Product("Milk", 1);
        syncProductDao.insertAll(milk);
        Product water = new Product("Water", 4);
        syncProductDao.insertAll(water);

        List<Product> products = syncProductDao.getOrderedProducts(1);
        assertEquals(products.get(0).getName(), milk.getName());

        products = syncProductDao.getOrderedProducts(5);
        assertEquals(products.get(0).getName(), milk.getName());
        assertEquals(products.get(1).getName(), lime.getName());
        assertEquals(products.get(2).getName(), water.getName());
    }

    @Test
    public void updateProduct() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);

        lime = syncProductDao.findByName("Lime");
        lime.setCurrentQuantity(3);
        syncProductDao.updateProduct(lime);

        Product limeInDb = syncProductDao.findByName("Lime");
        assertEquals(lime.getCurrentQuantity(), limeInDb.getCurrentQuantity(), 0);
    }

    @Test
    public void deleteProduct() throws Exception {
        Product lime = new Product("Lime", 2);
        syncProductDao.insertAll(lime);
        Product milk = new Product("Milk", 1);
        syncProductDao.insertAll(milk);
        Product water = new Product("Water", 4);
        syncProductDao.insertAll(water);

        milk = syncProductDao.findByName("Milk");
        syncProductDao.delete(milk);

        List<Product> products = syncProductDao.getAll();
        assertEquals(products.size(), 2);
    }
}