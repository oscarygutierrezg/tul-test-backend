package co.com.tul.test.ecommerce.util

import java.text.SimpleDateFormat
import java.util.Date

class Format {

	companion object {
		fun formadtTiem(d: Date): String = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(d)
	}
}