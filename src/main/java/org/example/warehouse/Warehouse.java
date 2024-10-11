package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;
    private final List<ProductRecord> products = new ArrayList<>();
    private final List<ProductRecord> changedProducts = new ArrayList<>();

    // Private constructor
    private Warehouse(String name) {
        this.name = name;
    }

    // Factory method for getting an instance
    public static Warehouse getInstance(String name) {
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    // Adding a product
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
        // Check if product with the same id already exists
        UUID finalUuid = uuid;
        if (products.stream().anyMatch(p -> p.uuid().equals(finalUuid))) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord(uuid, name, category, price);
        products.add(product);
        return product;
    }

    // Get all products
    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    // Check if warehouse is empty
    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Get a product by id
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream().filter(p -> p.uuid().equals(uuid)).findFirst();
    }

    // Update a product's price
    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        ProductRecord product = getProductById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Product with that id doesn't exist."));
        products.removeIf(p -> p.uuid().equals(uuid)); // Remove the old product
        ProductRecord updatedProduct = new ProductRecord(uuid, product.name(), product.category(), newPrice);
        products.add(updatedProduct); // Add the updated product
        changedProducts.add(updatedProduct);
    }

    // Get changed products
    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    // Get products by category
    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream()
                .filter(p -> p.category().equals(category))
                .collect(Collectors.toList());
    }

    // Get products grouped by category
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }
}
