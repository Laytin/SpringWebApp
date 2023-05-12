package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final Path root = Paths.get("./uploads");
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
        productList.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return productList;
    }
    public List<Product> getProducts(int page,String sort,String dir,String search){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findByNameContainingIgnoreCase(search,PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase())));
        productList.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return null;
    }
    public Product getProduct(int id) {
        Product res = productRepository.findById(id).orElse(null);
        res.setImageURLs(getImages(id));
        return res;
    }
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    private List<String> getImages(int id){

        List<String> result = load(id);
        if(result.isEmpty()){
            result = new ArrayList<>(Collections.singletonList(
                "https://www.shutterstock.com/image-vector/default-image-icon-thin-linear-260nw-2136460353.jpg"
        ));
        }

        return result;
    }
    @Transactional
    public void addProductToCart(CartProduct cartProduct) {
        //select * from product where product.name = (select name from product where product.id=2)
        // TODO: optimize
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        Session session = entityManager.unwrap(Session.class);
        Customer customer = session.load(Customer.class,customerId);
        cartProduct.setCustomer(customer);
        cartProductRepository.save(cartProduct);
    }
    @Transactional
    public int createProduct(Product product){
        productRepository.save(product);
        return product.getId();
    }

    public void updateProduct(int id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }
    @Transactional
    public void saveImages(int id,List<MultipartFile> files) {
        try {
            Path exist = Files.createDirectories(Path.of(root.toString() + "/" + id+"/"));
            files.forEach(file -> {
                if(file.isEmpty())
                    return;
                try{
                    byte[] bytes = file.getBytes();
                    Files.write(Path.of(exist.toString() + file.getOriginalFilename()), bytes);
                }catch (Exception e){}

            });
        } catch (IOException e) {
            throw new RuntimeException("Cant create a directory for product with id:"+id);
        }
    }
   public List<String> load(int id) {
        List<String> urlRes = new ArrayList<>();
       try  {
           Files.list(Paths.get(root.toString()+"/"+id+"/")).forEach(file -> {
               urlRes.add(file.toUri().toString());
           });
       } catch (IOException e) {
       }
       return urlRes;
   }
}
