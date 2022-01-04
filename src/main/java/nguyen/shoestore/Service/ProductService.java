package nguyen.shoestore.Service;

import nguyen.shoestore.Dto.ProductDTO;
import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Entity.Item;
import nguyen.shoestore.Entity.Product;
import  nguyen.shoestore.Repository.ItemRepo;
import nguyen.shoestore.Repository.ProductRepo;
import nguyen.shoestore.common.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ItemRepo itemRepo;

    public ProductService(ProductRepo productRepo, ItemRepo itemRepo) {
        this.productRepo = productRepo;
        this.itemRepo = itemRepo;
    }
    public List<Product> getAllProduct(){
        return productRepo.findByStatus(1);
    }
    public Optional<Product> findById(Integer productId){
        return productRepo.findById(productId);
    }
    public Product findByProductName(String productName){
        return productRepo.getByProductName(productName);
    }
    public List<Product> findByImportPrice(double importPrice){
        return productRepo.getByImportPrice(importPrice);
    }
    public List<Product> findByPrice(double price){
        return productRepo.getByPrice(price);
    }
    public List<Product> findByUpdater(String updater){
        return productRepo.getByUpdater(updater);
    }
    public Product findByUpdateTime(LocalDateTime dateTime){
        return productRepo.getByUpdateTime(dateTime);
    }
    @Transactional
    public ResponseDTO AddProduct (ProductDTO productDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = new Product();
        Assert.isNull(productRepo.getByProductName(productDTO.getProductName()),
                MessageUtils.getMessage("error.notfound",productDTO.getProductName()));
        product.setProductName(productDTO.getProductName());
        product.setImportPrice(productDTO.getImportPrice());
        product.setPrice(productDTO.getPrice());
        product.setUpdaterId(productDTO.getUpdaterId());
        product.setStatus(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        product.setUpdateTime(simpleDateFormat.format(new Date()));
        productRepo.save(product);
        responseDTO.setCode(1);
        responseDTO.setMessage("success");
    //TODO : them updater.
        return responseDTO;
    }

    @Transactional
    public ResponseDTO deleteProduct(Integer id){
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = productRepo.getById(id);
        Assert.notNull(product, MessageUtils.getMessage("error.notfound",product));
        if(product.getStatus()==1){
            List<Item> items = itemRepo.getByProductId(id);
            Assert.notNull(items, MessageUtils.getMessage("error.notfound",items));
            for(Item item : items){
                item.setStatus(0);
                itemRepo.save(item);
                product.setStatus(0);
                responseDTO.setCode(1);
                responseDTO.setMessage("success");
            }
        }else {
            responseDTO.setCode(0);
            responseDTO.setMessage("fail");
        }
        return responseDTO;
    }
}
