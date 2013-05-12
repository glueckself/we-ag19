package userDB;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("userDB.DateConverter")
public class DateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String stringValue) throws ConverterException {
        Pattern p = Pattern.compile("^(0?[1-9]|[1-2][0-9]|30|31)\\.(0?[1-9]|1[0-2])\\.(\\d{4})$");
        Matcher m = p.matcher(stringValue);

        if (m.find()) {
            int day = Integer.parseInt(m.group(1));
            int month = Integer.parseInt(m.group(2));
            int year = Integer.parseInt(m.group(3));

            // Note: Month is 0-based
            return new GregorianCalendar(year, month-1, day);
        } else {
            throw new ConverterException();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object objectValue) throws ConverterException {
        if (objectValue == null || !(objectValue instanceof GregorianCalendar))
            return "";

        GregorianCalendar date = (GregorianCalendar)objectValue;
        return String.format("%02d.%02d.%04d", date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH)+1, date.get(Calendar.YEAR));
    }
}