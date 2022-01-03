package nguyen.shoestore.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Time_keeping")
public class TimeKeeping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private Integer checkId;
    @Column(name = "id_staff")
    private Integer idStaff;
    @Column(name = "check_in")
    private Integer checkIn;
    @Column(name = "check_out")
    private Integer checkOut;
    @Column(name = "check_day")
    private Integer checkDay;
    @Column(name = "id_manager")
    private Integer idManager;
    @Column(name = "confirm")
    private boolean confirm;
}
