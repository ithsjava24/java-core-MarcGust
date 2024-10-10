package org.example.warehouse;

import jdk.internal.icu.text.UnicodeSet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.example.warehouse.Category.instances;

/* X Skapa en instans via en getInstance-metod
Hantera produkter (X lägga till, uppdatera, hämta)
Användare ska inte kunna ändra produkterna*/
public class Warehouse {

    private final String name;
    private final Map<UUID, ProductRecord> products = new HashMap<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (name == null) {
            throw new IllegalArgumentException("Product must have a name");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product must have a category");
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product already exists");
        }
        ProductRecord newProduct = new ProductRecord(uuid, name, category, price);
        products.put(uuid, newProduct);
        return newProduct;
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        ProductRecord product = products.get(uuid);
        if (product == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        ProductRecord updatedProduct = new ProductRecord(product.uuid(), product.name(), product.category(), newPrice);
        products.put(uuid, updatedProduct);
        changedProducts.add(updatedProduct);
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



    public boolean getChangedProducts() {
        return false;
    }

    public boolean getProductsGroupedByCategories() {
        return false;
    }
}
