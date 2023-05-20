package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final Path root = Paths.get("./uploads/products");
    private final Logger log = LogManager.getLogger(ProductService.class);
    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public ProductService(ProductRepository productRepository, CartProductRepository cartProductRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.entityManager = entityManager;
    }
    public void initImageStorage() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    public List<Product> getProducts(int page,String sort,String dir){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findAll(PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase()))).getContent();
        enrichProductImages(productList);
        return productList;
    }
    public List<Product> getProducts(int page,String sort,String dir,String search){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findByNameContainingIgnoreCase(search,PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase())));
        enrichProductImages(productList);
        return null;
    }
    public Product getProduct(int id) {
        Product res = productRepository.findById(id).orElse(null);
        enrichProductImages(new ArrayList<Product>(Collections.singletonList(res)));
        return res;
    }
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    @Transactional
    public void addProductToCart(CartProduct cartProduct, CustomerDetails principal) {
        Session session = entityManager.unwrap(Session.class);
        Customer customer = session.load(Customer.class,principal.getCustomer().getId());
        cartProduct.setCustomer(customer);
        cartProductRepository.save(cartProduct);
    }
    @Transactional
    public int createProduct(Product product, CustomerDetails cd){
        productRepository.save(product);
        log.info("Created new product:"+product.toString()+" by ["+cd.getCustomer().toString()+"]");
        return product.getId();
    }

    public void updateProduct(int id, Product product, CustomerDetails cd) {
        product.setId(id);
        log.info("Product has been updated:"+product.toString()+" by ["+cd.getCustomer().toString()+"]");
        productRepository.save(product);
    }
    @Transactional
    public void saveImages(int id,List<MultipartFile> files, CustomerDetails cd) {
        files.forEach(file -> {
            if(file.isEmpty())
                return;
            try{
                byte[] bytes = file.getBytes();
                String sname = root.toString() + "/"+id+"/" + file.getOriginalFilename();
                Files.write(Path.of(sname), bytes);
                log.info("Added image for product with ID:"+id +", Image:" +sname+" by ["+cd.getCustomer().toString()+"]");
            }catch (Exception e){}
        });
    }
    @Transactional
    public void deleteImage(int id, String filename, RedirectAttributes ra, CustomerDetails cd) {
        try {
            Files.deleteIfExists(Path.of(root.toString() + "/"+id+"/" + filename));
            Files.deleteIfExists(Path.of(filename));
            ra.addFlashAttribute("message", "Successfully deleted");
            log.info("Deleted image for product with ID:"+id +", Image:" +filename+" by ["+cd.getCustomer().toString()+"]");
        } catch (IOException e) {
            ra.addFlashAttribute("message", "Error:"+ Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
    private void enrichProductImages(List<Product> productList){
        productList.forEach(Product::loadImages);
    }
}
