package com.github.vinicius2335.connect.api.utils;

import net.datafaker.Faker;

import java.util.Locale;

public final class FakerUtils {

    private FakerUtils() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static Faker FAKER = new Faker(Locale.getDefault());
}
