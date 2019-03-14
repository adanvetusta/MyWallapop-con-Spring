package com.uniovi.validators;

import com.uniovi.entities.Product;
import com.uniovi.entities.User;
import com.uniovi.services.ProductsService;
import com.uniovi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class AddProductFormValidator implements Validator {
	
	@Autowired
	private ProductsService productsService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Product product = (Product) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Error.empty");
		
		if (product.getTitle().length() < 5 || product.getTitle().length() > 24) {
			errors.rejectValue("title", "Error.addProduct.title.length");
		}
		if (product.getTitle().length() < 10) {
			errors.rejectValue("description", "Error.addProduct.description.length");
		}
		if (product.getPrice() <= 0) {
			errors.rejectValue("price", "Error.addProduct.price.quantity");
		}

	}
}