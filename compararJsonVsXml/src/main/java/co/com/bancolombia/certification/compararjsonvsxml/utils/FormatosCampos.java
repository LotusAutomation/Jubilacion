package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatosCampos {

	public FormatosCampos() {
		super();
	}

	public String formatText(String valueField) {
		String valueTemp = valueField;

		if (valueField != null) {
			valueTemp = valueField.replaceAll("\"", "'");
			valueTemp = valueTemp.replaceAll("\t", "");
			valueTemp = valueTemp.replaceAll("\n", "");
			valueTemp = valueTemp.replaceAll("\\\\", "/");

		}
		return valueTemp;
	}

	public String formatDateString(String value) {

		value = value.replace(".", "");
		String dateAux = "";
		SimpleDateFormat originalFormat;

		// SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
		SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

		try {
			originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			date = originalFormat.parse(value.trim());
			dateAux = newFormat.format(date);

		} catch (ParseException e) {

			originalFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {

				date = originalFormat.parse(value.trim());
				dateAux = newFormat.format(date);

			} catch (ParseException e1) {

				originalFormat = new SimpleDateFormat("dd-MM-yyyy");
				try {

					date = originalFormat.parse(value.trim());
					dateAux = newFormat.format(date);

				} catch (ParseException e2) {
					originalFormat = new SimpleDateFormat("yyyyMMdd");
					try {
						date = originalFormat.parse(value.trim());
						dateAux = newFormat.format(date);
					} catch (ParseException e3) {
						e3.printStackTrace();
					}
				}
			}
		}
		return dateAux;
	}

	public String formatNumber(String valueField) {

		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());

		formatSymbols.setDecimalSeparator(',');
		DecimalFormat df = new DecimalFormat("#,##0.#######", formatSymbols);
		String valueFormatter = valueField;

		if (valueField.contains(".")) {
			valueFormatter = df.format(Double.parseDouble(valueField));
		}
		return valueFormatter;
	}

	public String formatDate(String valueFieldJSON, boolean time, boolean isXML) {
		System.out.println("================================================");
		String dateAux = "";
		SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		// SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("valueFieldJSON..." + valueFieldJSON);
		if (!time) {
			//newFormat = new SimpleDateFormat("yyyyMMdd");
			 newFormat = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("!time..." + newFormat);
		}
		System.out.println("HOLU 1");
		if (isXML) {
			originalFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss,SSX");
			System.out.println("isXML..." + originalFormat);
		}

		System.out.println("HOLU 2");
		if (isXML && !time) {
			//originalFormat = new SimpleDateFormat("dd/MM/yyyy");
			//Se cambia formato("dd/MM/yyyy") por ("yyyyMMdd") para COMEX_19
			originalFormat = new SimpleDateFormat("yyyyMMdd");
			System.out.println("isXML && !time..." + originalFormat);
		}
		System.out.println("HOLU 3");

//		if (!isXML && !time) {
//			// originalFormat = new SimpleDateFormat("yyyyMMdd");
//			System.out.println("!isXML || time..." + newFormat.toString());
//			newFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//			System.out.println("!isXML || time.2.." + newFormat.toString());
//
//		}
//		System.out.println("HOLU 4");
		if (!isXML || time) {
			// originalFormat = new SimpleDateFormat("yyyyMMdd");
			System.out.println("!isXML || time..." + newFormat);
			newFormat = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("!isXML || time..." + newFormat);
		}
		System.out.println("HOLU 6");

		try {
			Date date = originalFormat.parse(valueFieldJSON.trim());
			System.out.println("Date..." + date.toString());
			dateAux = newFormat.format(date);
			System.out.println("dateAux..." + dateAux.toString());
		} catch (java.text.ParseException ex) {
			Logger.getLogger(ElementosXml.class.getName()).log(Level.SEVERE, null, ex);
		}
		// System.out.println("formatDate dateAux::." + dateAux);
		return dateAux;
	}

	public ArrayList<String> originalArray(ArrayList<String> valueArrList) {

		String[] valueArr = (String[]) valueArrList.toArray(new String[valueArrList.size()]);
		String[] newValueArr = Arrays.stream(valueArr).filter(value -> value != null && value.length() > 0)
				.toArray(size -> new String[size]);
		ArrayList<String> newVal = new ArrayList<String>(Arrays.asList(newValueArr));
		return newVal;
	}

	public String extractOnlyFileName(String nameAnexo) {

		String[] arrOnlyFileName = null;
		String onlyFileName = nameAnexo;

		try {
			onlyFileName = nameAnexo.replaceAll("\\\\", "/");
			arrOnlyFileName = onlyFileName.split("/");
			onlyFileName = arrOnlyFileName[((arrOnlyFileName.length) - 1)];

		} catch (Exception e) {
			System.out.println(".::ERROR::.onlyFileName " + e);
		}

		return onlyFileName;
	}
}
