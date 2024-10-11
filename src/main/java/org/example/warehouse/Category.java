package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Category {
    private final String name;

    // Cachen för att hålla instanser av Category
    private static final Map<String, Category> CACHE = new HashMap<>();

    // Privat konstruktor för att förhindra skapande av instanser utanför klassen
    private Category(String name) {
        this.name = name;
    }

    // Statisk metod för att skapa en instans av Category
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be empty.");
        }

        // Kolla om kategorin redan finns i cachen
        String capitalized = capitalizeFirstLetter(name);
        return CACHE.computeIfAbsent(capitalized, Category::new);
    }

    // Getter för name, för att möta tester som förväntar sig name()
    public String name() {
        return name;
    }

    // Hjälpmetod för att kapitalisera den första bokstaven
    private static String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name; // Hantera tomma strängar
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
