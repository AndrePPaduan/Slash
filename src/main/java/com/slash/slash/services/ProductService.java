package com.slash.slash.services;

import com.slash.slash.exceptions.ProducDoesNotExist;
import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.exceptions.ProductHasNoName;
import com.slash.slash.models.Product;

import java.util.List;

public interface ProductService {

    public Product addProduct (Product product, String companyName) throws ProductAlreadyExists, ProductHasNoName;
    public void deleteProduct (String productName) throws ProducDoesNotExist;
    public Product editProduct(String productName, Product newProduct) throws ProducDoesNotExist, ProductAlreadyExists;
    public List<Product> listProducts();
    public List<Product> listProductByType(String type);
    public List<Product> listProductByCity(String city);
    public Product retrieveProductByName(String name);
}
