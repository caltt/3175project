package com.example.a3175.utils;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal toBigDecimal(long value) {
        return new BigDecimal(value);
    }

    @TypeConverter
    public static long fromBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.longValue();
    }
}
