package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

import static org.example.warehouse.Category.instances;

/* X Skapa en instans via en getInstance-metod
Hantera produkter (lägga till, uppdatera, hämta)
Användare ska inte kunna ändra produkterna*/
public class Warehouse {

    private final String name;

    public Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (uuid == null) {
            throw new IllegalArgumentException("Product must have a UUID");
        }
        if (name == null) {
            throw new IllegalArgumentException("Product must have a name");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product must have a category");
        }
        if (price == null) {
            throw new IllegalArgumentException("Product must have a price");
        }
        ProductRecord productRecord = new ProductRecord(uuid, name, category, price);
        ProductRecord newProduct = null;
        return newProduct;
    }

    public boolean getProductsBy(Category meat) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean getProducts() {
        return false;
    }



    public boolean getProductById(UUID uuid) {
        return false;
    }

    public void updateProductPrice(UUID uuid, BigDecimal bigDecimal) {
    }

    public boolean getChangedProducts() {
        return false;
    }

    public boolean getProductsGroupedByCategories() {
        return false;
    }
}
