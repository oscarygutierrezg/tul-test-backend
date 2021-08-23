package co.com.tul.test.ecommerce.validation


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class NumberNotNullValidator : ConstraintValidator<NumberNotNull, Int>{
	
	override fun isValid( value: Int,  context: ConstraintValidatorContext) : Boolean {
	 return  value != 0;
	}
}