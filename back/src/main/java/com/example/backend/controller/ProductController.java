package com.example.backend.controller;

import com.example.backend.dto.ProductDTO;
import com.example.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing product-related operations.
 * Author: Youssef Elouafi
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Creates or updates a product.
     *
     * @param productDTO The product to be saved.
     * @return A ResponseEntity containing the saved product and HTTP status code.
     */
    @PostMapping
    @Operation(summary = "Create or update a product", description = "Create a new product or update an existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created or updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves all products from the database.
     *
     * @return A ResponseEntity containing the list of products and HTTP status code.
     */
    @GetMapping
    @Operation(summary = "Retrieve all products", description = "Get a list of all products in the system")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to be retrieved.
     * @return A ResponseEntity containing the found product and HTTP status code.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a product by ID", description = "Get a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /**
     * Partially updates a product.
     *
     * @param id          The ID of the product to be updated.
     * @param productDTO  The partial product details to be updated.
     * @return A ResponseEntity containing the updated product and HTTP status code.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a product", description = "Update selected fields of an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to be deleted.
     * @return A ResponseEntity with HTTP status code indicating the outcome of the operation.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}