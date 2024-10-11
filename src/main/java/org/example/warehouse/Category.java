package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category {
    private final String name;

    private static final Map<String, Category> CACHE = new HashMap<>();

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be empty.");
        }

        String capitalized = capitalizeFirstLetter(name);
        return CACHE.computeIfAbsent(capitalized, Category::new);
    }

    public String name() {
        return name;
    }

    private static String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
