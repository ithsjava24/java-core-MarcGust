package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

import static org.example.warehouse.Category.instances;

public class Warehouse {
    private final String name;
    private final Map<UUID, Products> products = new HashMap<>();
    private final Set<Products> changedProducts = new HashSet<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public Products addProduct(UUID uuid, String name, Category category, BigDecimal price) {
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

        Products newProduct = new Products(uuid, name, category, price);
        products.put(uuid, newProduct);
        return newProduct;
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        Products product = products.get(uuid);
        if (product == null) {
            throw new IllegalArgumentException("Product does not exist");
        }

        Products updatedProduct = new Products(product.uuid(), product.name(), product.category(), newPrice);
        products.put(uuid, updatedProduct);
        changedProducts.add(updatedProduct);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<Products> getProducts() {
        return List.copyOf(products.values());
    }

    public Optional<Products> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    public Set<Products> getChangedProducts() {
        return Collections.unmodifiableSet(changedProducts);
    }

    public Map<Category, List<Products>> getProductsGroupedByCategories() {
        Map<Category, List<Products>> groupedByCategory = new HashMap<>();
        for (Products product : products.values()) {
            groupedByCategory.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return groupedByCategory;
    }
}
