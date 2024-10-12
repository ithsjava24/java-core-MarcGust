package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private static Map<String, Category> categories = new HashMap<>();
    private String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) throws IllegalArgumentException {

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

    private static String capitalize(String string) {

        if (string.isEmpty())
            return string;

        if (string.length() == 1)
            return string.toUpperCase();

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public String name() {
        return "";
    }
}
