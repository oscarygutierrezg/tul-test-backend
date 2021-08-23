package co.com.tul.test.ecommerce.validation


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class FloatNumberNotNegativeValidator : ConstraintValidator<FloatNumberNotNegative, Float>{
	
	override fun isValid( value: Float,  context: ConstraintValidatorContext) : Boolean =  value >= 0;
	
}