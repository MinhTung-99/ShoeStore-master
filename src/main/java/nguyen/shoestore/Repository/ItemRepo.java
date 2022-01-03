package nguyen.shoestore.Repository;


import nguyen.shoestore.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.*;
import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Integer> {
    List<Item> findByStatus(Integer status);
    List<Item> getByProductId (Integer productId);
    @Query("select i from Item i where i.productId in (SELECT productId FROM Product where productName =: productName)")
    List<Item> getByProductName (String productName);
//    List<Item> findByColorId (String color);
//    List<Item> getByColorId (String color);
//    @Query("select i from Item i where i.color =: colorName")
//    List<Item> getByColorName (String colorName);
    List<Item> getBySize (Integer size);
    List<Item> getByNumItems (Integer numItems);
    List<Item> getBySale (Integer sale);
    Item getByColorAndAndSize(String color,Integer size);
}
