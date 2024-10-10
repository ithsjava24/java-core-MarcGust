package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private static final Map<String, Category> instances = new HashMap<>();
    private final String name;
    /*Ha en of-metod som skapar och returnerar en instans.
    Se till att namnet alltid börjar med en versal.
    Använda en singleton-struktur, där samma kategori med samma namn alltid returnerar samma instans.
    X Kasta ett IllegalArgumentException om namnet är null.
    X Inte ha någon publik konstruktor.*/
    private Category() {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        return instances.computeIfAbsent(name.toLowerCase(), Category::new);
    }

    public String getName() {
        return name;
    }
}
