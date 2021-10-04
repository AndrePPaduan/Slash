package com.slash.slash.services;

import com.slash.slash.FileManagement.FileController;
import com.slash.slash.exceptions.*;
import com.slash.slash.models.Company;
import com.slash.slash.models.Product;
import com.slash.slash.models.ProductDisplayer;
import com.slash.slash.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileController fileController;


    @Override
    public Product addProduct(Product product, String companyName, MultipartFile file) throws ProductAlreadyExists, ProductHasNoName, CompanyDoesNotExist {
        List<Product> productList = listProducts();

        if (product.getName() == null) {
            throw new ProductHasNoName();
        }

        for (Product savedProduct : productList) {
            if (savedProduct.getName().equals(product.getName())) {
                throw new ProductAlreadyExists();
            }
        }

        Company company = companyService.retrieveCompanyByName(companyName);
        if (company == null) throw new CompanyDoesNotExist();

        companyService.addProduct(companyName, product);
        product.setCompany(companyService.retrieveCompanyByName(companyName));
        productRepository.save(product);
        fileController.uploadFile(file, product.getId(), product.getImageLink());

        return product;
    }

    @Override
    public void deleteProduct(String productName) throws ProducDoesNotExist {
        Product product = retrieveRealProductByName(productName);
        if (product != null) {
            Company company = product.getCompany();
            company.deleteProduct(product);
            productRepository.delete(product);
        } else {
            throw new ProducDoesNotExist();
        }
    }

    @Override
    public Product editProduct(String productName, Product newProduct) throws ProducDoesNotExist, ProductAlreadyExists {
        Product oldProduct = retrieveRealProductByName(productName);

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
    public ProductDisplayer retrieveProductByName(String name) throws ProducDoesNotExist {
        Product realProduct = retrieveRealProductByName(name);
        ProductDisplayer productDisplayer = convertProductForDisplay(realProduct);

        return productDisplayer;
    }

    private ProductDisplayer convertProductForDisplay(Product realProduct) {
        ProductDisplayer productDisplayer = new ProductDisplayer();

        productDisplayer.setName( realProduct.getName());
        productDisplayer.setCompanyName(realProduct.getCompany().getName());
        productDisplayer.setDescription(realProduct.getDescription());
        productDisplayer.setImageLink(realProduct.getImageLink());
        productDisplayer.setType(realProduct.getType());
        productDisplayer.setImage(fileController.downloadFile(realProduct.getId(), realProduct.getImageLink(), ));

        return productDisplayer;
    }


    public Product retrieveRealProductByName(String name) throws ProducDoesNotExist {
        List<Product> productList = listProducts();

        for (Product product : productList) {
            if (product.getName().equals(name)){
                return product;
            }
        }
        throw new ProducDoesNotExist();
    }
}
