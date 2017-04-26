package com.losK.backend;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by m.losK on 2017-04-26.
 */
@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean b) {
        return String.valueOf(b);
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return false;
    }

}
