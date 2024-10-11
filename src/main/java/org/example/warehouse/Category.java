package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private static final Map<String, Category> categories = new HashMap<>();
    private final String name;

    // Private constructor
    private Category(String name) {
        this.name = name;
    }

    // Factory method for creating or getting a Category
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        // Ensure first letter is uppercase
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return categories.computeIfAbsent(name, Category::new);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
