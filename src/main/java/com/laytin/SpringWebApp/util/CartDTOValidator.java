package com.laytin.SpringWebApp.util;

import com.laytin.SpringWebApp.dto.CartDTORequest;
import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.services.CartService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;


@Component
public class CartDTOValidator implements Validator {
    private final CartService cartService;

    public CartDTOValidator(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CartDTORequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartDTORequest cartDTORequest = (CartDTORequest) target;
        List<CartProduct> cartProductList = cartService.getCart(); //need recall from db to fetch product quantity(just in case)
        for(int i =0; i<cartDTORequest.getProductList().size(); i++) {
            int dtoid = cartDTORequest.getProductList().get(i).getId();
            CartProduct cp = cartProductList.stream().filter(p -> p.getId()==dtoid).findFirst().get();
            if(cp.getQuantity()>cp.getProduct().getQuantity()){
                errors.pushNestedPath("productList["+i+"]");
                errors.rejectValue("quantity", "","Quantity changed of:" + cp.getProduct().getName()+","+cp.getProduct().getColor()+". Stock:" +
                        cp.getProduct().getQuantity());
                errors.popNestedPath();
            }
        }
    }
}
