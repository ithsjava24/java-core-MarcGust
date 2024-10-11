package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;
    private final List<ProductRecord> products = new ArrayList<>();
    private final List<ProductRecord> changedProducts = new ArrayList<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        UUID finalUuid = uuid;
        if (products.stream().anyMatch(p -> p.uuid().equals(finalUuid))) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord(uuid, name, category, price);
        products.add(product);
        return product;
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream().filter(p -> p.uuid().equals(uuid)).findFirst();
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        ProductRecord product = getProductById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Product with that id doesn't exist."));
        products.removeIf(p -> p.uuid().equals(uuid));
        ProductRecord updatedProduct = new ProductRecord(uuid, product.name(), product.category(), newPrice);
        products.add(updatedProduct);
        changedProducts.add(updatedProduct);
    }

    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream()
                .filter(p -> p.category().equals(category))
                .collect(Collectors.toList());
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }
}
