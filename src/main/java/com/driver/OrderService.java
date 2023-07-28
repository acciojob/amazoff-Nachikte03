package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderService {

    HashMap<String,Order> idOrderMap;
    HashMap<String,DeliveryPartner> idDeliveryPartnerMap;
    HashMap<String, List<String>> deliveryIdOrderListMap;
    HashMap<String,String> orderIdDeliveryIdMap;

    public OrderService(){
        idOrderMap = new HashMap<>();
        idDeliveryPartnerMap = new HashMap<>();
        deliveryIdOrderListMap = new HashMap<>();
        orderIdDeliveryIdMap = new HashMap<>();
    }

    public void addOrder(Order order){
        String id = order.getId();
        idOrderMap.put(id,order);
    }

    public void addPartner(String id){
        DeliveryPartner deliveryPartner = new DeliveryPartner(id);
        idDeliveryPartnerMap.put(id,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId,String deliverPartnerId){
        orderIdDeliveryIdMap.put(orderId,deliverPartnerId);
        if(deliveryIdOrderListMap.containsKey(deliverPartnerId)){
            deliveryIdOrderListMap.get(deliverPartnerId).add(orderId);
        }
        else{
            deliveryIdOrderListMap.put(deliverPartnerId,new ArrayList<>(){{add(orderId);}});
        }
        DeliveryPartner deliveryPartner = idDeliveryPartnerMap.get(deliverPartnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
    }

    public Order getOrderById(String id){
        return idOrderMap.get(id);
    }

    public DeliveryPartner getPartnerById(String id){
        return idDeliveryPartnerMap.get(id);
    }

    public Integer getOrderCountByPartnerId(String id){
        return idDeliveryPartnerMap.get(id).getNumberOfOrders();
    }

        public List<String> getOrdersByPartnerId(String id){
        return deliveryIdOrderListMap.get(id);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(idOrderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders(){
        Integer total = idOrderMap.size();
        Integer p = orderIdDeliveryIdMap.size();
        return total-p;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String p,String partnerId){
        int deliveryTime = 60*(Integer.parseInt(p.substring(0,2)))+(Integer.parseInt(p.substring(3,5)));
        List<String> orderId = deliveryIdOrderListMap.get(partnerId);
        int k = 0;
        for(String id:orderId){
            if(idOrderMap.get(id).getDeliveryTime()>=deliveryTime){
                k++;
            }
        }
        return k;
    }


    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int k = 0;
        List<String> p = deliveryIdOrderListMap.get(partnerId);
        for(String id:p){
            Order order = idOrderMap.get(id);
            k = Math.max(k,order.getDeliveryTime());
        }
        return k/60 + ":" + k%60;
    }

    public void deletePartnerById(String partnerId){
        DeliveryPartner deliveryPartner = idDeliveryPartnerMap.get(partnerId);
        idDeliveryPartnerMap.remove(partnerId);
        List<String> p1 = deliveryIdOrderListMap.get(partnerId);
        deliveryIdOrderListMap.remove(partnerId);
        for(String id:p1){
            orderIdDeliveryIdMap.remove(id);
        }
    }

    public void deleteOrderById(String id){
        Order order = idOrderMap.get(id);
        String pankya = orderIdDeliveryIdMap.get(id);
        idOrderMap.get(id);
        if(pankya!=null)
        orderIdDeliveryIdMap.remove(pankya);
        List<String> p = deliveryIdOrderListMap.get(pankya);
        DeliveryPartner k = idDeliveryPartnerMap.get(pankya);
        if(k!=null)
        k.setNumberOfOrders(k.getNumberOfOrders()-1);
        if(order!=null)
        p.remove(id);
    }



}
