    package org.example.warehouse;

    import java.math.BigDecimal;
    import java.util.UUID;

    public record ProductRecord(UUID id, String name, Category category, BigDecimal price) {

        public UUID uuid() {
            return id; // Returnerar id som UUID
        }

        public ProductRecord withPrice(BigDecimal newPrice) {
            return new ProductRecord(this.id, this.name, this.category, newPrice);
        }
    }
