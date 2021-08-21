package co.com.tul.test.ecommerce.validation


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class NumberNotNullValidator : ConstraintValidator<NumberNotNull, Float>{
	
	override fun isValid( value: Float,  context: ConstraintValidatorContext) : Boolean {
		println("Test "+(value != 0.0F))
	 return  value != 0.0F;
	}
}