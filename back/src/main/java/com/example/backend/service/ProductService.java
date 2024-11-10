package com.example.backend.service;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;
import com.example.backend.exception.ProductNotFoundException;
import com.example.backend.mapper.ProductMapper;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing products.
 * Auteur: Youssef Elouafi
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a product and return the saved product as a DTO.
     *
     * @param productDTO the product to save.
     * @return the saved product as a DTO.
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product savedProduct = productRepository.save(ProductMapper.INSTANCE.toEntity(productDTO));
        return ProductMapper.INSTANCE.toDTO(savedProduct);
    }

    /**
     * Get all products and return them as a list of DTOs.
     *
     * @return a list of products as DTOs.
     */
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.toDTOs(products);
    }

    /**
     * Get a product by ID and return it as a DTO.
     *
     * @param id the ID of the product to retrieve.
     * @return the product as a DTO.
     */
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.INSTANCE.toDTO(product);
    }

    /**
     * Partially updates a product by setting the ID and saving the product directly.
     *
     * @param id          The ID of the product to be updated.
     * @param productDTO  The product details to be updated.
     * @return The updated product.
     */
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = ProductMapper.INSTANCE.toEntity(productDTO);
            product.setId(id);
            product = productRepository.save(product);
            return ProductMapper.INSTANCE.toDTO(product);
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    /**
     * Delete a product by its ID.
     *
     * @param id the ID of the product to delete.
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}