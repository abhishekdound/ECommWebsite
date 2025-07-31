package com.example.ABD.EcomSpring.Service;

import com.example.ABD.EcomSpring.Model.Product;
import com.example.ABD.EcomSpring.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProduceService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product>productList(){
        return productRepo.findAll();
    }

    public Product getProduct(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImage(image.getBytes());
        return productRepo.save(product);
    }

    public byte[] getImage(int id) {
        Product product=productRepo.findById(id).orElse(null);
        if(product!=null){
            return product.getImage();
        }
        return null;
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public Product updateProduct(Product product, MultipartFile multipartFile, int id) throws IOException {
        product.setImage(multipartFile.getBytes());
        product.setImageType(multipartFile.getContentType());
        product.setImageName(multipartFile.getOriginalFilename());
        productRepo.deleteById(id);
        return productRepo.save(product);
    }

    public List<Product> findByKeyword(String keyword, String keyword1, String keyword2) {
        return productRepo.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword1, keyword2,keyword);

    }
}
