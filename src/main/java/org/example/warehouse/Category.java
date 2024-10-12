package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private static final Map<String, Category> categories = new HashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) throws IllegalArgumentException {

        // Check if name is null
        if (name == null)
            throw new IllegalArgumentException("Category name can't be null");

        name = capitalize(name);

        if (!categories.containsKey(name)) {
            categories.put(name, new Category(name));
        }

        return categories.get(name);
    }

    public String getName(){
        return name;
    }

    private static String capitalize(String str) {

        if (str.isEmpty())
            return str;

        if (str.length() == 1)
            return str.toUpperCase();

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public String name() {
        return "";
    }
}
