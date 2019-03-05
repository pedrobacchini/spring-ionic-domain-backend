package com.github.pedrobacchini.springionicdomain.resource.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList(String idsString) {
        return Arrays.stream(idsString.split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    public static String decodeParam(String string) {
        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
