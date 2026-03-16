package com.tms.controller;

import com.tms.model.Product;
import com.tms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/sort/{sortField}")
    public ResponseEntity<List<Product>> getAllProductsBySortField(@PathVariable("sortField") String sortField) {
        List<Product> products = productService.getAllProductsWithSort(sortField);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/pagination/{page}/{size}")
    public ResponseEntity<Page<Product>> getAllProductsByPaginationField(@PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Product> products = productService.getAllProductsWithPagination(page, size);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<HttpStatus> addProduct(@PathVariable Integer userId, @PathVariable Integer productId) {
        Boolean isAdded = productService.addProductToUser(userId, productId);
        if (isAdded) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
