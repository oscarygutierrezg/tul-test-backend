package co.com.tul.test.ecommerce.validation


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class NumberNotNegativeValidator : ConstraintValidator<NumberNotNegative,Int>{
	
	override fun isValid( value: Int,  context: ConstraintValidatorContext) : Boolean =  value.toInt() >= 0;
	
}