package com.example.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Product.
 * This is a record type, making it immutable and compact.
 * Auteur: Youssef Elouafi
 */
public record ProductDTO(
        Long id,
        @NotNull(message = "Product code cannot be null") String code,
        @NotNull(message = "Product code cannot be null") String name,
        @Size(max = 300) String description,
        String image,
        String category,
        @Positive(message = "Price must be positive") double price,
        @Min(0) int quantity,
        String internalReference,
        int shellId,
        String inventoryStatus,
        int rating,
        long createdAt,
        long updatedAt) {

    public ProductDTO {
        if (quantity == 0) {
            quantity = 0;
        }
    }
}
