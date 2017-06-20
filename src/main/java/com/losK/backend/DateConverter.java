package com.losK.backend;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by m.losK on 2017-04-26.
 */
@Converter
public class DateConverter implements AttributeConverter<Date, String> {

    @Override
    public String convertToDatabaseColumn(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
        return dateFormat.format(date);
    }

    @Override
    public Date convertToEntityAttribute(String stringDate) {
        DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
