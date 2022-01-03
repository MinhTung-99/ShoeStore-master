package nguyen.shoestore.controller;

import nguyen.shoestore.Dto.OrderDTO;
import nguyen.shoestore.Dto.ProductDTO;
import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Entity.Product;
import nguyen.shoestore.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private ProductService productService;
    @Autowired

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @PostMapping(value = "/add")
    public ResponseDTO addProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO response = new ResponseDTO();
        response = productService.AddProduct(productDTO);
        return response;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseDTO deleteProduc(@PathVariable(name = "id") Integer productId) {
        ResponseDTO response = new ResponseDTO();
        response = productService.deleteProduct(productId);
        return response;
    }
}
