package com.altenecommerce.controller;

import com.altenecommerce.entity.Product;
import com.altenecommerce.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Produits", description = "Opérations CRUD sur les produits")

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Lister tous les produits")
    @GetMapping
    public List<Product> all() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Obtenir un produit par ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> one(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un nouveau produit (admin uniquement)")
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product, Principal principal) {
        String email = principal.getName(); 

        if (!email.equals("admin@admin.com")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Product created = productService.createProduct(product);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Modifier un produit (admin uniquement)")
    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @RequestBody Product product,
                                          Principal principal) {
        String email = principal.getName(); 
        if (!email.equals("admin@admin.com")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Supprimer un produit (admin uniquement)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        String email = principal.getName();

        if (!email.equals("admin@admin.com")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
