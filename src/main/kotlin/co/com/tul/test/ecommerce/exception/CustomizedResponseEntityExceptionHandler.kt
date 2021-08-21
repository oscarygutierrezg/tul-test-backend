package co.com.tul.test.ecommerce.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ResponseEntity
import co.com.tul.test.ecommerce.exception.model.CustomErrorResponse
import java.time.LocalDateTime
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.validation.FieldError
import co.com.tul.test.ecommerce.util.Format
import java.util.Date
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(value = [(Exception::class)])
	fun handleAllExceptions(ex: Exception,request: WebRequest): ResponseEntity<CustomErrorResponse>  {
		var servletWebRequest = request as ServletWebRequest
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomErrorResponse( Format.formadtTiem(Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.message!!,servletWebRequest.getRequest().getRequestURL().toString()));
	}
	
	@ExceptionHandler(value = [(Exception::class)])
	fun exception(ex: Exception): ResponseEntity<CustomErrorResponse>  {
	
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomErrorResponse( Format.formadtTiem(Date()), HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.message!!,""));
	}
	
	@ExceptionHandler(value = [(ProductNotFoundException::class)])
	fun handleAllExceptions(ex: ProductNotFoundException,request: WebRequest): ResponseEntity<CustomErrorResponse>  {
		var servletWebRequest = request as ServletWebRequest
		return ResponseEntity.status(ex.statusCode).body(CustomErrorResponse( Format.formadtTiem(Date()),ex.statusCode.value(),ex.reason,servletWebRequest.getRequest().getRequestURL().toString()));
	}
	
	

	override fun handleMethodArgumentNotValid( ex: MethodArgumentNotValidException,
			 headers: HttpHeaders,  status: HttpStatus,  request: WebRequest):  ResponseEntity<Any> {
		var sb =  StringBuilder("Validation Failed ");
		ex.bindingResult.allErrors.forEach{
			var  fieldError =  it as FieldError;	
		
			sb.append(fieldError.getField()+" "+it.defaultMessage+" ");
		}
		var servletWebRequest = request as ServletWebRequest
	
		return ResponseEntity.status(status).body(CustomErrorResponse( Format.formadtTiem(Date()),status.value(),sb.toString(),servletWebRequest.getRequest().getRequestURL().toString()));
	} 
}