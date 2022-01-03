package nguyen.shoestore.Service;


import nguyen.shoestore.Dto.ItemDTO;
import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Entity.Item;
import nguyen.shoestore.Repository.*;
import nguyen.shoestore.common.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final nguyen.shoestore.Repository.ItemRepo itemRepo;
    private final ProductRepo productRepo;

    public ItemService(nguyen.shoestore.Repository.ItemRepo itemRepo, ProductRepo productRepo) {
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
    }
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }
    public List<Item> getAllItemsActive() {
        return itemRepo.findByStatus(1);
    }
    public List<Item> getAllItemsUnActive() {
        return itemRepo.findByStatus(0);
    }
    public Optional<Item> findById(Integer itemId) {
        return itemRepo.findById(itemId);
    }
    public List<Item> findByProductName(String productName) {
        return itemRepo.getByProductName(productName);
    }
//    public List<Item> findByColorName(String colorName) {
//        return itemRepo.getByColorName(colorName);
//    }
    public List<Item> findBySize(Integer size) {
        return itemRepo.getBySize(size);
    }
    public List<Item> findByNumItems(Integer numItems) {
        return itemRepo.getByNumItems(numItems);
    }
    public List<Item> findBySale(Integer sale) {
        return itemRepo.getBySale(sale);
    }
    @Transactional
    public ResponseDTO AddItem (ItemDTO itemDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Item item = new Item();
        Assert.notNull(productRepo.getByProductName(itemDTO.getProductName()),
                MessageUtils.getMessage("error.notfound",itemDTO.getProductName()));
        Item item1 = itemRepo.getByColorAndAndSize(itemDTO.getColorName(),itemDTO.getSize());
        if(item1 == null){
            setItem(itemDTO, item);
        }else {
            itemDTO.setNumItems(itemDTO.getNumItems() + item1.getNumItems());
            setItem(itemDTO, item1);
        }
        return responseDTO;
    }

    public void setItem(ItemDTO itemDTO, Item item) {
        item.setProductId(productRepo.getByProductName(itemDTO.getProductName()).getProductId());
        item.setColor(itemDTO.getColorName());
        item.setSize(itemDTO.getSize());
        item.setNumItems(itemDTO.getNumItems());
        item.setSale(itemDTO.getSale());
        item.setStatus(1);
        itemRepo.save(item);
    }

    @Transactional
    public ResponseDTO DeleteItem(Integer itemId) {
        ResponseDTO responseDTO = new ResponseDTO();
        Item item = itemRepo.getById(itemId);
        Assert.notNull(item, MessageUtils.getMessage("error.notfound",itemId));
        item.setStatus(0);
        itemRepo.save(item);
        return responseDTO;
    }
    @Transactional
    public ResponseDTO UpdateItem(ItemDTO itemDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Item item = itemRepo.getById(itemDTO.getItemId());
        Assert.notNull(item, MessageUtils.getMessage("error.notfound",itemDTO.getItemId()));
        setItem(itemDTO, item);
        return responseDTO;
    }
    @Transactional
    public ResponseDTO updateImg(MultipartFile file, Integer itemId){
        ResponseDTO responseDTO = new ResponseDTO();
        Item item = itemRepo.getById(itemId);
        Assert.notNull(item,MessageUtils.getMessage("error.notfound",itemId));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(". .")){
            responseDTO.setMessage("not a valid file");
        }else {
            try {
                item.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemRepo.save(item);
            responseDTO.setMessage("success");
        }
        return responseDTO;
    }
}
