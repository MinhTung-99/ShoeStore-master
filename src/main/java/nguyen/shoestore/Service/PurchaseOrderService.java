package nguyen.shoestore.Service;


import nguyen.shoestore.Dto.OrderDTO;
import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Dto.ResponseOrderDTO;
import nguyen.shoestore.Entity.Item;
import nguyen.shoestore.Entity.PurchaseOrder;
import nguyen.shoestore.Entity.User;
import nguyen.shoestore.Repository.*;
import  nguyen.shoestore.Repository.ItemRepo;
import  nguyen.shoestore.Repository.UserRepo;
import nguyen.shoestore.common.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ItemRepo itemRepo;

    public PurchaseOrderService(PurchaseOrderRepo orderRepo, UserRepo userRepo, ItemRepo itemRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
    }
    @Transactional
    public List<ResponseOrderDTO> getAllPurchaseOrders() {
        List<ResponseOrderDTO> responseOrderDTOs = new ArrayList<>();
        List<PurchaseOrder> orderList = orderRepo.findAll();
        if(!orderList.isEmpty()){
            for (PurchaseOrder order: orderList) {
                ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
                responseOrderDTO.setCodeOrder(order.getCodeOrder());
                Optional<User> costumer = userRepo.findById(order.getIdUser());
                responseOrderDTO.setUser(costumer.orElseThrow());
//                responseOrderDTO.user.setId(costumer.orElseThrow().getId());
//                responseOrderDTO.user.setFullName(costumer.orElseThrow().getFullName());
//                responseOrderDTO.user.setGender(costumer.orElseThrow().getGender());
//                responseOrderDTO.user.setEmail(costumer.orElseThrow().getEmail());
//                responseOrderDTO.user.setPhoneNumber(costumer.orElseThrow().getPhoneNumber());
                Optional<User> admin = userRepo.findById(order.getIdStaff());
                responseOrderDTO.setStaff(admin.orElseThrow());
//                responseOrderDTO.staff.setId(admin.orElseThrow().getId());
//                responseOrderDTO.staff.setFullName(admin.orElseThrow().getFullName());
//                responseOrderDTO.staff.setGender(admin.orElseThrow().getGender());
//                responseOrderDTO.staff.setEmail(admin.orElseThrow().getEmail());
//                responseOrderDTO.staff.setPhoneNumber(admin.orElseThrow().getPhoneNumber());
                Optional<Item> item = itemRepo.findById(order.getItemId());
                responseOrderDTO.setItem(item.orElseThrow());
//                responseOrderDTO.item.setItemId(item.orElseThrow().getItemId());
//                responseOrderDTO.item.setColor(item.orElseThrow().getColor());
//                responseOrderDTO.item.setSize(item.orElseThrow().getSize());
//                responseOrderDTO.item.setProductId(item.orElseThrow().getProductId());
//                responseOrderDTO.item.setImg(item.orElseThrow().getImg());
//                responseOrderDTO.item.setSale(item.orElseThrow().getSale());
                responseOrderDTO.setQuantity(order.getPurchaseQuantity());
                responseOrderDTO.setMoney(order.getMoney());
                responseOrderDTO.setProcess(order.getProcess());
                responseOrderDTOs.add(responseOrderDTO);
            }
        }
        return responseOrderDTOs;
    }
    public Optional<PurchaseOrder> findPurchaseOrderById(Integer codeOrder) {
        return orderRepo.findById(codeOrder);
    }
    public List<PurchaseOrder> findPurchaseOrderByUser(String user) {
        return orderRepo.getByUser(user);
    }
    public List<PurchaseOrder> findPurchaseOrderByStaff(String staff) {
        return orderRepo.getByStaff(staff);
    }
    public List<PurchaseOrder> findPurchaseOrderByItemId(Integer itemId) {
        return orderRepo.getByItemId(itemId);
    }

    public PurchaseOrder findPurchaseOrderByCreateTime(LocalDateTime dateTime){
        return orderRepo.getByCreateTime(dateTime);
    }
    public List<PurchaseOrder> findWaitOrderByProcess(){

        return orderRepo.getByProcess(0);
    }
    public List<PurchaseOrder> findOKOrderByProcess(){

        return orderRepo.getByProcess(1);
    }
    @Transactional
    public ResponseDTO AddOder (OrderDTO orderDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        Assert.notNull(userRepo.getById(orderDTO.getUser()),
                MessageUtils.getMessage("error.notfound",orderDTO.getUser()));
        Item item = itemRepo.getById(orderDTO.getItemId());
        Assert.notNull(item, MessageUtils.getMessage("error.notfound",orderDTO.getItemId()));
        setOrder(orderDTO, purchaseOrder);
        int oldNumber = item.getNumItems();
        item.setNumItems(oldNumber-orderDTO.getQuantity());
        itemRepo.save(item);
        orderRepo.save(purchaseOrder);
        return responseDTO;
    }

    public void setOrder(OrderDTO orderDTO,PurchaseOrder purchaseOrder ) {
        purchaseOrder.setIdUser(orderDTO.getUser());
        purchaseOrder.setItemId(orderDTO.getItemId());
        purchaseOrder.setPurchaseQuantity(orderDTO.getQuantity());
        purchaseOrder.setMoney(orderDTO.getMoney());
        purchaseOrder.setProcess(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        purchaseOrder.setCreateTime(simpleDateFormat.format(new Date()));
        purchaseOrder.setStatus(1);
    }

    @Transactional
    public ResponseDTO DeleteOrder(Integer orderId) {
        ResponseDTO responseDTO = new ResponseDTO();
        PurchaseOrder purchaseOrder = orderRepo.getById(orderId);
        Assert.notNull(purchaseOrder, MessageUtils.getMessage("error.notfound",orderId));
        purchaseOrder.setStatus(0);
        orderRepo.save(purchaseOrder);
        return responseDTO;
    }

    @Transactional
    public ResponseDTO confirm (OrderDTO orderDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        PurchaseOrder order = orderRepo.getById(orderDTO.getCodeOrder());
        Assert.notNull(order, MessageUtils.getMessage("error.notfound",orderDTO.getCodeOrder()));
        Assert.notNull((orderDTO.getStaff()),
                MessageUtils.getMessage("error.notfound",orderDTO.getStaff()));
        order.setIdStaff(orderDTO.getStaff());
        order.setProcess(1);
        responseDTO.setMessage("success");
        return responseDTO;
    }
}
