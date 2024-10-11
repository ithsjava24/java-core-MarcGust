package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecord(UUID id, String name, Category category, BigDecimal price) {

    // En metod för att hämta UUID
    public UUID uuid() {
        return id; // Returnerar id som UUID
    }

    // En metod för att uppdatera priset
    public ProductRecord withPrice(BigDecimal newPrice) {
        return new ProductRecord(this.id, this.name, this.category, newPrice);
    }
}
