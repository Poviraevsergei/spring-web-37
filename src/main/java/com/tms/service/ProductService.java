package com.tms.service;

import com.tms.model.Product;
import com.tms.model.User;
import com.tms.repository.ProductRepository;
import com.tms.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsWithSort(String field) {
        if (field == null) {
            field = "id";
        }
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Product> getAllProductsWithPagination(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    //Both(Paging and sorting)
    public Page<Product> getAllProducts(int page, int size, String field, Sort.Direction sortDirection) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(sortDirection, field)));
    }

    public Boolean addProductToUser(Integer userId, Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        if (product.isEmpty() || user.isEmpty()) {
            throw new ValidationException("Invalid Product id or User Id");
        }

        return productRepository.addProductToUser(userId, productId) != 0;
    }
}
