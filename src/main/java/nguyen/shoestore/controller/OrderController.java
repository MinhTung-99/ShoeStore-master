package nguyen.shoestore.controller;

import nguyen.shoestore.Dto.OrderDTO;
import nguyen.shoestore.Dto.ProductDTO;
import nguyen.shoestore.Dto.ResponseDTO;
import nguyen.shoestore.Entity.PurchaseOrder;
import nguyen.shoestore.Service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    private PurchaseOrderService orderService;
    @Autowired
    public OrderController(PurchaseOrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<PurchaseOrder> getAllOrder() {
        return orderService.getAllPurchaseOrders();
    }
    @GetMapping(value = "/id")
    public Optional<PurchaseOrder> findOrderById(@RequestParam Integer orderId) {
        return orderService.findPurchaseOrderById(orderId);
    }
    @GetMapping(value = "/costumer")
    public List<PurchaseOrder> findOrderByCostumer(@RequestParam String costumer){
        return orderService.findPurchaseOrderByUser(costumer);
    }
    @GetMapping(value = "/staff")
    public List<PurchaseOrder> findOrderByStaff(@RequestParam String staff){
        return orderService.findPurchaseOrderByStaff(staff);
    }
    @GetMapping(value = "/item")
    public List<PurchaseOrder> findOrderByItemId(@RequestParam Integer itemId){
        return orderService.findPurchaseOrderByItemId(itemId);
    }
    @GetMapping(value = "/quantity")
    public PurchaseOrder findOrderByCreateTime(@RequestParam LocalDateTime dateTime){
        return orderService.findPurchaseOrderByCreateTime(dateTime);
    }
    @GetMapping(value = "/process/wait")
    public List<PurchaseOrder> findOrderByProcess(){
        return orderService.findWaitOrderByProcess();
    }
    @GetMapping(value = "/process/ok")
    public List<PurchaseOrder> findOrderByProcessOk(){
        return orderService.findOKOrderByProcess();
    }
    @PostMapping(value = "/add")
    public ResponseDTO addOrder(@RequestBody OrderDTO orderDTO) {
        ResponseDTO response = new ResponseDTO();
        response = orderService.AddOder(orderDTO);
        return response;
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDTO deleteItem(@PathVariable(name = "id") Integer orderId) {
        ResponseDTO response = new ResponseDTO();
        response = orderService.DeleteOrder(orderId);
        return response;
    }
    @PutMapping(value = "/cofirm")
    public ResponseDTO confirm(@RequestBody OrderDTO orderDTO) {
        ResponseDTO response = new ResponseDTO();
        response = orderService.confirm(orderDTO);
        return response;
    }
}
