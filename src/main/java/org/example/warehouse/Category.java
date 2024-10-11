package org.example.warehouse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Category {
    private static final Map<String, Category> categories = new HashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        String formattedName = capitalize(name);
        return categories.computeIfAbsent(formattedName, Category::new);
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getName() {
        return name;
    }

    public static Map<String, Category> getCategories() {
        return Collections.unmodifiableMap(categories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
