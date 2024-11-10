package com.example.backend.mapper;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;
import com.example.backend.enums.InventoryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Mapper for converting Product entity to ProductDTO and vice versa.
 * Auteur: Youssef Elouafi
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "inventoryStatus", source = "inventoryStatus", qualifiedByName = "inventoryStatusToString")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToTimestamp")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToTimestamp")
    ProductDTO toDTO(Product product);

    @Mapping(target = "inventoryStatus", source = "inventoryStatus", defaultExpression = "java(InventoryStatus.valueOf(productDTO.inventoryStatus()))")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapTimestampToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapTimestampToLocalDateTime")
    Product toEntity(ProductDTO productDTO);

    /**
     * Converts an InventoryStatus enum to its string representation.
     *
     * @param inventoryStatus the inventory status enum value.
     * @return the name of the inventory status as a string, or null if inventoryStatus is null.
     */
    @Named("inventoryStatusToString")
    default String inventoryStatusToString(InventoryStatus inventoryStatus) {
        return (inventoryStatus != null) ? inventoryStatus.name() : null;
    }

    /**
     * Converts a timestamp in milliseconds to LocalDateTime.
     *
     * @param timestamp the timestamp in milliseconds.
     * @return the LocalDateTime representation of the timestamp, or null if timestamp is null.
     */
    @Named("mapTimestampToLocalDateTime")
    default LocalDateTime mapTimestampToLocalDateTime(Long timestamp) {
        return (timestamp != null) ? LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC) : null;
    }

    /**
     * Converts a LocalDateTime to a timestamp in milliseconds.
     *
     * @param localDateTime the LocalDateTime to be converted.
     * @return the timestamp in milliseconds, or null if localDateTime is null.
     */
    @Named("mapLocalDateTimeToTimestamp")
    default Long mapLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        return (localDateTime != null) ? localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }


    /**
     * Converts a list of Product entities to a list of ProductDTOs.
     *
     * @param products the list of products to convert.
     * @return the list of ProductDTOs.
     */
    List<ProductDTO> toDTOs(List<Product> products);

    /**
     * Converts a list of ProductDTOs to a list of Product entities.
     *
     * @param productDTOs the list of ProductDTOs to convert.
     * @return the list of Product entities.
     */
    List<Product> toEntities(List<ProductDTO> productDTOs);
}
