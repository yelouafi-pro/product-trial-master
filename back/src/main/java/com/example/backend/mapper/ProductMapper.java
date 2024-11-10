package com.example.backend.mapper;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;
import com.example.backend.enums.InventoryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for converting Product entity to ProductDTO and vice versa.
 * Auteur: Youssef Elouafi
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "inventoryStatus", source = "inventoryStatus", qualifiedByName = "inventoryStatusToString")
    ProductDTO toDTO(Product product);

    @Mapping(target = "inventoryStatus", source = "inventoryStatus", defaultExpression = "java(InventoryStatus.valueOf(productDTO.inventoryStatus()))")
    Product toEntity(ProductDTO productDTO);

    @Named("inventoryStatusToString")
    default String inventoryStatusToString(InventoryStatus inventoryStatus) {
        if (inventoryStatus != null) {
            return inventoryStatus.name();
        }
        return null;
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
