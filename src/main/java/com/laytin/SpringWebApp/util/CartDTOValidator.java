package com.laytin.SpringWebApp.util;

import com.laytin.SpringWebApp.dto.CartDTORequest;
import com.laytin.SpringWebApp.models.CartProduct;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class CartDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CartDTORequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartDTORequest cartDTORequest = (CartDTORequest) target;
        for(int i =0; i<cartDTORequest.getProductList().size(); i++) {
            CartProduct cp = cartDTORequest.getProductList().get(i);
            if(cp.getQuantity()>cp.getProduct().getQuantity()){
                errors.pushNestedPath("productList["+i+"]");
                errors.rejectValue("quantity", "","Quantity changed of:" + cp.getProduct().getName()+","+cp.getProduct().getColor());
                errors.popNestedPath();
            }
        }
    }
}
