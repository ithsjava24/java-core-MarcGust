package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

import static org.example.warehouse.Category.instances;

/*Skapa en instans via en getInstance-metod
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

    public boolean getProductsBy(Category meat) {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean getProducts() {
        return false;
    }

    public ProductRecord addProduct(UUID uuidMilk, String milk, Category dairy, BigDecimal bigDecimal) {
        return null;
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
