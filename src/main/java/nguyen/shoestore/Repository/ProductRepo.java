package nguyen.shoestore.Repository;

import nguyen.shoestore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product getByProductName (String productName);
    List<Product> getByImportPrice(double importPrice);
    List<Product> getByPrice(double price);
    List<Product> getByUpdaterId (Integer updaterId);
    @Query("select p from  Product p where p.updaterId in (SELECT id from  User where fullName =: updater)")
    List<Product> getByUpdater (String updater);
    Product getByUpdateTime (LocalDateTime updateTime);
    List<Product> findByStatus(Integer status);
}
