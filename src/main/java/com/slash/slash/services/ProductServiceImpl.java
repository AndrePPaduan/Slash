package com.slash.slash.services;

import com.slash.slash.exceptions.ProducDoesNotExist;
import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.exceptions.ProductHasNoName;
import com.slash.slash.exceptions.UserHasNoName;
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

    @Autowired
    private CompanyService companyService;


    @Override
    public Product addProduct(Product product, String companyName) throws ProductAlreadyExists, ProductHasNoName {
        List<Product> productList = listProducts();

        if (product.getName() == null) {
            throw new ProductHasNoName();
        }

        for (Product savedProduct : productList) {
            if (savedProduct.getName().equals(product.getName())) {
                throw new ProductAlreadyExists();
            }
        }
        companyService.addProduct(companyName, product);
        product.setCompany(companyService.retrieveCompanyByName(companyName));
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productName) throws ProducDoesNotExist {
        Product product = retrieveProductByName(productName);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new ProducDoesNotExist();
        }
    }

    @Override
    public Product editProduct(String productName, Product newProduct) throws ProducDoesNotExist, ProductAlreadyExists {
        Product oldProduct = retrieveProductByName(productName);

        if (oldProduct == null) {
            throw new ProducDoesNotExist();
        }

        List<Product> productList = productRepository.findAll();
        for (Product savedProduct : productList) {
            if (savedProduct.getName().equals(newProduct.getName()) && oldProduct.getId() != savedProduct.getId()) {
                throw new ProductAlreadyExists();
            }
        }

        oldProduct.setName(newProduct.getName());
        oldProduct.setType(newProduct.getType());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setCompany(newProduct.getCompany());
        oldProduct.setImageLink(newProduct.getImageLink());

        productRepository.save(oldProduct);

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
