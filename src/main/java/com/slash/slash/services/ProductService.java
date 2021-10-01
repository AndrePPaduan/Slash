package com.slash.slash.services;

import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.models.Product;

import java.util.List;

public interface ProductService {

    public Product addProduct (Product product) throws ProductAlreadyExists;
    public void deleteProduct (Product product);
    public Product editProduct(Product oldProduct, Product newProduct);
    public List<Product> listProducts();
    public List<Product> listProductByType(String type);
    public List<Product> listProductByCity(String city);
    public Product retrieveProductByName(String name);
}
