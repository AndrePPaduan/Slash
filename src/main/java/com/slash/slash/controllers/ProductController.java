package com.slash.slash.controllers;

import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.exceptions.ProductHasNoName;
import com.slash.slash.models.Product;
import com.slash.slash.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(Product product) throws ProductAlreadyExists, ProductHasNoName {
        Product createdProduct = productService.addProduct(product);
        return  new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(Product product) {
        productService.deleteProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<?> editProduct(Product oldProduct, Product newProduct) {
        productService.editProduct(oldProduct, newProduct);
        return new ResponseEntity<>(oldProduct, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<?> listProducts() {
        List<Product> productList = productService.listProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/type/{type}")
    public ResponseEntity<?> listProductByType(String type) {
        List<Product> productList = productService.listProductByType(type);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/city/{city}")
    public ResponseEntity<?> listProductByCity(String city) {
        List<Product> productList = productService.listProductByCity(city);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<?> retrieveProductByName(String name) {
        Product product = productService.retrieveProductByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
