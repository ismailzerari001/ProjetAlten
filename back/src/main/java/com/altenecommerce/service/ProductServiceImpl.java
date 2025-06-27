package com.altenecommerce.service;

import com.altenecommerce.entity.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File jsonFile = new File("src/main/resources/products.json");

    private List<Product> readProducts() {
        try {
            if (!jsonFile.exists()) return new ArrayList<>();
            return objectMapper.readValue(jsonFile, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture JSON", e);
        }
    }

    private void writeProducts(List<Product> products) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, products);
        } catch (IOException e) {
            throw new RuntimeException("Erreur d'écriture JSON", e);
        }
    }

    @Override
    public Product createProduct(Product product) {
        List<Product> products = readProducts();
        product.setId(generateNextId(products));
        products.add(product);
        writeProducts(products);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return readProducts();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return readProducts().stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        List<Product> products = readProducts();
        Optional<Product> optional = products.stream()
            .filter(p -> Objects.equals(p.getId(), id)).findFirst();

        optional.ifPresent(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setPrice(updatedProduct.getPrice());
            existing.setDescription(updatedProduct.getDescription());
            existing.setCategory(updatedProduct.getCategory());
        });

        writeProducts(products);
        return optional;
    }

    @Override
    public void deleteProduct(Long id) {
        List<Product> products = readProducts();
        products.removeIf(p -> Objects.equals(p.getId(), id));
        writeProducts(products);
    }

    private Long generateNextId(List<Product> products) {
        return products.stream()
                .map(Product::getId)
                .filter(Objects::nonNull)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }
}
