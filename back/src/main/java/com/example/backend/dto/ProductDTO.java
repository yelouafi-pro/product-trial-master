package com.example.backend.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Product.
 * This is a record type, making it immutable and compact.
 * Auteur: Youssef Elouafi
 */
public record ProductDTO(Long id, String code, String name, String description, String image, String category,
                         double price, int quantity, String internalReference, int shellId,
                         String inventoryStatus, int rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
