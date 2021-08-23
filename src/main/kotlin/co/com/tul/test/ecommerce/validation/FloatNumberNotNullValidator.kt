package co.com.tul.test.ecommerce.validation


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class FloatNumberNotNullValidator : ConstraintValidator<FloatNumberNotNull, Float>{
	
	override fun isValid( value: Float,  context: ConstraintValidatorContext) : Boolean {
	 return  value != 0.0F;
	}
}