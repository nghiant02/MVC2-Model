/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiant.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author localboss
 */
public class CartObj implements Serializable{
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }
    
    //bo do vao trong gio
    public boolean addItemToCart(String id, int quantity){
        boolean result = false;
        //1.Check cac doi so truyen vao co hop le kh?(check data validation)
        if(id == null){
            return result;
        }
        if(id.trim().isEmpty()){
            return result;
        }
        if(quantity <= 0){
            return result;
        }
        //2.check ngan chua do (exist item)
        if(this.items == null){
            this.items = new HashMap<>();
        }
        //3.Check existed item
        if(this.items.containsKey(id)){//coi co ton tai hay khong
            //4.increase quantity
            int currentQuantity = this.items.get(id);
            quantity = quantity + currentQuantity;
        }//end item has existed
        //update item
        this.items.put(id, quantity);
        result = true;
        
        return result;
    }
    
    public boolean removeItemFromCart(String id, int quantity){
        boolean result = false;
         if(id == null){
            return result;
        }
        if(id.trim().isEmpty()){
            return result;
        }
        if(quantity <= 0){
            return result;
        }
        //2.check existed items
        if(this.items == null){
            return result;
        } //day la buoc check ngan chua do co ton tai hay kh?
        //3.check existed item
        if(!this.items.containsKey(id)){
            return result;
        }
        //4.decrease quantity
        int currentQuantity = this.items.get(id);
        
        if(currentQuantity >= quantity){
            quantity = currentQuantity - quantity;
        }//neu lon hon thi moi giam item
        //5.update cart
        if(quantity == 0){
            this.items.remove(id);
            if(this.items.isEmpty()){
                this.items = null;
            }
        } else{
            this.items.put(id, quantity);
        }
        result = true;
        
        return result;
    }
}
