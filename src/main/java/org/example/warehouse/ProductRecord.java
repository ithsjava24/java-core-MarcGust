package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

//UUID, Name, Category, Price
public record ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
}
