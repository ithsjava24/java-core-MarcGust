package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private final Map<UUID, ProductRecord> products = new HashMap<>();
    private final List<ProductRecord> changedProducts = new ArrayList<>();
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        ProductRecord product = new ProductRecord(uuid, name, category, price);
        if (products.putIfAbsent(uuid, product) != null) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        return product;
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("New price can't be null.");
        }

        ProductRecord existingProduct = getProductById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Product with that id doesn't exist."));

        ProductRecord updatedProduct = new ProductRecord(uuid, existingProduct.name(), existingProduct.category(), newPrice);
        products.put(uuid, updatedProduct);
        changedProducts.add(updatedProduct);
    }

    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(p -> p.category().equals(category))
                .collect(Collectors.toList());
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }
}
