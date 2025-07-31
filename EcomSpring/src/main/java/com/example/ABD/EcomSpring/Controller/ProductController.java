package com.example.ABD.EcomSpring.Controller;
import com.example.ABD.EcomSpring.Service.ProduceService;
import com.example.ABD.EcomSpring.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductController {
    @Autowired
    private ProduceService produceService;

    @GetMapping("product/{id}/image")
    @ResponseBody
    public ResponseEntity<byte[]>getImage(@PathVariable("id")int id){
        byte[] image=produceService.getImage(id);
        if(image!=null){
            return new ResponseEntity<>(image,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping("/products")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProduct(){
        return new ResponseEntity<>(produceService.productList(),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable("id")int id){
        Product product=produceService.getProduct(id);
        if(product==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/product")
    @ResponseBody
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product,@RequestPart("imageFile") MultipartFile imageFile){
        Product savedProduct= null;
        try {
            savedProduct = produceService.addProduct(product,imageFile);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/product/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProduct(@PathVariable("id")int id){
        produceService.deleteProduct(id);
        return new ResponseEntity<>("Product with id " + id + " deleted succesfully" , HttpStatus.OK);
    }
    @PutMapping("product/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@PathVariable("id")int id,@RequestPart Product product,@RequestPart MultipartFile imageFile){
        try {
            Product product1=produceService.updateProduct(product,imageFile,id);
            return new ResponseEntity<>(product1,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("products/search")
    @ResponseBody
    public ResponseEntity<List<Product>> searchByKeyword(@RequestParam("keyword")String keyword){
        List<Product>productList=produceService.findByKeyword(keyword,keyword,keyword);
        return new ResponseEntity<>(productList,HttpStatus.OK);

    }
}
