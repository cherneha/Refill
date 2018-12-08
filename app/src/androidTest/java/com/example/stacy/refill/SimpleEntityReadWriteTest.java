package com.example.stacy.refill;

import static org.hamcrest.CoreMatchers.is;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.ProductDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private ProductDao productDao;
    private AppDatabase appDB;

    @Before
    public void createDb() {
        Context context = getTargetContext().getApplicationContext();
        appDB = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        productDao = appDB.productDao();
    }

    @After
    public void closeDb() throws IOException {
        appDB.close();
    }

    @Test
    public void saveData() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);

        Product milk = new Product("Milk", 1);
        productDao.insertAll(milk);
        List<Product> products = productDao.getAll();

        assertEquals(2, products.size());
        assertEquals(products.get(0).getName(), lime.getName());
        assertEquals(products.get(1).getName(), milk.getName());
    }

    @Test
    public void findByName() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);

        Product byName = productDao.findByName("Lime");
        assertEquals(lime.getCurrentQuantity(), byName.getCurrentQuantity(), 0);
        assertEquals(lime.getName(), byName.getName());
        assertEquals(lime.getDailyUpdate(), byName.getDailyUpdate(), 0);
        assertEquals(lime.getLastUpdate(), byName.getLastUpdate());
        assertEquals(1, byName.getId());
    }

    @Test
    public void loadByIds() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);

        Product milk = new Product("Milk", 1);
        productDao.insertAll(milk);
        List<Product> products = productDao.getAll();
        int[] ids = {1, 2};
        List<Product> productsByIds = productDao.loadAllByIds(ids);
        assertEquals(products.size(), productsByIds.size());
    }

    @Test
    public void getOrdered() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);
        Product milk = new Product("Milk", 1);
        productDao.insertAll(milk);
        Product water = new Product("Water", 4);
        productDao.insertAll(water);

        List<Product> products = productDao.getOrderedProducts(1);
        assertEquals(products.get(0).getName(), milk.getName());

        products = productDao.getOrderedProducts(5);
        assertEquals(products.get(0).getName(), milk.getName());
        assertEquals(products.get(1).getName(), lime.getName());
        assertEquals(products.get(2).getName(), water.getName());
    }

    @Test
    public void updateProduct() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);

        lime = productDao.findByName("Lime");
        lime.setCurrentQuantity(3);
        productDao.updateProduct(lime);

        Product limeInDb = productDao.findByName("Lime");
        assertEquals(lime.getCurrentQuantity(), limeInDb.getCurrentQuantity(), 0);
    }

    @Test
    public void deleteProduct() throws Exception {
        Product lime = new Product("Lime", 2);
        productDao.insertAll(lime);
        Product milk = new Product("Milk", 1);
        productDao.insertAll(milk);
        Product water = new Product("Water", 4);
        productDao.insertAll(water);

        milk = productDao.findByName("Milk");
        productDao.delete(milk);

        List<Product> products = productDao.getAll();
        assertEquals(products.size(), 2);
    }
}