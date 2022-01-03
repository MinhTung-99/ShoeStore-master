package nguyen.shoestore.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "import_price")
    private double importPrice;
    @Column(name = "price")
    private double price;
    @Column(name = "updater_id")
    private Integer updaterId;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "status")
    private Integer status;
}
