package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private final String name;
    private final Map<UUID, ProductRecord> products = new HashMap<>();
    private final List<ProductRecord> changedProducts = new ArrayList<>();

    private static Warehouse instance;

    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        if (instance == null) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (price == null) {
            price = BigDecimal.ZERO;
        }

        ProductRecord product = new ProductRecord(id, name, category, price);
        if (products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        products.put(product.id(), product);
        return product;
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        ProductRecord product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        ProductRecord updatedProduct = product.withPrice(newPrice);
        products.put(id, updatedProduct);

        if (!changedProducts.contains(updatedProduct)) {
            changedProducts.add(updatedProduct);
        }
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    public Optional<ProductRecord> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> filteredProducts = new ArrayList<>();
        for (ProductRecord product : products.values()) {
            if (product.category().equals(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> groupedProducts = new HashMap<>();
        for (ProductRecord product : products.values()) {
            groupedProducts.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return groupedProducts;
    }
}
