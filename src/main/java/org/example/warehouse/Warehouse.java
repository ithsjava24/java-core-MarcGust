package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private static Map<String, Warehouse> warehouses = new HashMap<>();
    private List<ProductRecord> products = new ArrayList<>();
    private List<ProductRecord> changedProducts = new ArrayList<>();
    private String name;

    private Warehouse(){}

    private Warehouse(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Warehouse getInstance() {
        return new Warehouse();
    }

    public static Warehouse getInstance(String warehouseName) throws IllegalArgumentException {

        // Check if name is null or empty
        if (warehouseName == null) {
            throw new IllegalArgumentException("Warehouse name can't be null");
        }

        warehouseName = capitalize(warehouseName);

        if (!warehouses.containsKey(warehouseName)) {
            warehouses.put(warehouseName, new Warehouse(warehouseName));
        }

        return warehouses.get(warehouseName);
    }

    public ProductRecord addProduct(UUID uuid, String productName, Category category, BigDecimal price) throws IllegalArgumentException {

        getProductById(uuid).ifPresent(_ -> {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        });

        while (uuid == null || getProductById(uuid).isPresent()){
            uuid = UUID.randomUUID();
        }

        ProductRecord productRecord = new ProductRecord(uuid, productName, category, price);
        products.add(productRecord);

        return productRecord;
    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(products);
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream()
                .filter(productRecord -> productRecord.uuid().equals(uuid))
                .findFirst();
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) throws IllegalArgumentException {

        getProductById(uuid).ifPresentOrElse(
                productRecord -> {
                    changedProducts.add(productRecord);
                    products.remove(productRecord);
                    addProduct(uuid, productRecord.product(), productRecord.category(), newPrice);
                },
                () -> {
                    throw new IllegalArgumentException("Product with that id doesn't exist.");
                }
        );
    }

    public List<ProductRecord> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream().collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return getProductsGroupedByCategories().getOrDefault(category, List.of());
    }

    private static String capitalize(String str) {

        if (str.isEmpty())
            return str;

        if (str.length() == 1)
            return str.toUpperCase();

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
