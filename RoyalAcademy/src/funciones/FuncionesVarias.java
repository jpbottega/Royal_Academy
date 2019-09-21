package funciones;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FuncionesVarias {
	public static String getStringDate(java.util.Date utilDate, int formato) {
		String format = "";
		switch (formato) {
		case 0:
			format = "dd/MM/yyyy HH:mm:ss";
			break;
		case 1:
			format = "dd/MM/yyyy";
			break;
		case 2:
			format = "yyyy/MM/dd";
			break;
		case 3:
			format = "EEE, MMM d HH:mm ";
			break;
		case 4:
			format = "yyyy-MM-dd HH:mm:ss";
			break;
		case 5:
			format = "yyyy-MM-dd";
			break;
		case 6:
			format = "EEEE dd/MM/yyyy";
			break;
		case 7:
			format = "EEEE dd 'de' MMMM 'del' yyyy";
			break;
		case 8:
			format = "yyyyMMdd";
			break;
		default:
			format = "dd/MM/yyyy HH:mm:ss";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("es", "ES"));

		String sqlDate = "";
		try {
			sqlDate = sdf.format(utilDate);
		} catch (Exception e) {
			sqlDate = sdf.format(Calendar.getInstance().getTime());
		}

		return sqlDate;
	}
}
