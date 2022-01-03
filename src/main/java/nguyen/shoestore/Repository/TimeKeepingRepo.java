package nguyen.shoestore.Repository;

import nguyen.shoestore.Entity.TimeKeeping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeKeepingRepo extends JpaRepository<TimeKeeping, Integer> {
    List<TimeKeeping> getByIdStaff(Integer idStaff);
//    List<TimeKeeping> getByCheckIn(Date checkIn);
//    List<TimeKeeping> getByCheckOut(LocalDateTime checkOut);
//    List<TimeKeeping> getByCheckDay(String checkDay);
//    List<TimeKeeping> getByIdManager(Integer idManager);
//    List<TimeKeeping> getByConfirm(Integer confirm);
}
