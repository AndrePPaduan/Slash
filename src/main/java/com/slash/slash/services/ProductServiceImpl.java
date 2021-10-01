package com.slash.slash.services;

import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.models.Product;
import com.slash.slash.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) throws ProductAlreadyExists {
        List<Product> productList = listProducts();
        for (Product savedProduct : productList) {
            if (savedProduct.getName().equals(product.getName())) {
                throw new ProductAlreadyExists();
            }
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        if (product != null) {
            productRepository.delete(product);
        } else {
            System.out.println("Product does not exist");
        }
    }

    @Override
    public Product editProduct(Product oldProduct, Product newProduct) {

        oldProduct.setName(newProduct.getName());
        oldProduct.setType(newProduct.getType());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setCompany(newProduct.getCompany());
        oldProduct.setImageLink(newProduct.getImageLink());

        return oldProduct;
    }


    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> listProductByType(String type) {

        List<Product> productList = listProducts();
        List<Product> selectedProducts = new LinkedList<>();

        for (Product product : productList) {
            if (product.getType().equals(type)) {
                selectedProducts.add(product);
            }
        }
        return selectedProducts;
    }

    @Override
    public List<Product> listProductByCity(String city) {

        List<Product> productList = listProducts();
        List<Product> selectedProducts = new LinkedList<>();

        for (Product product : productList) {
            if (product.getCompany().getCity().equals(city)) {
                selectedProducts.add(product);
            }
        }
        return selectedProducts;

    }

    @Override
    public Product retrieveProductByName(String name) {
        List<Product> productList = listProducts();

        for (Product product : productList) {
            if (product.getName().equals(name)) return product;
        }
        return null;
    }
}
