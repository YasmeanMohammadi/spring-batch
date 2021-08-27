package com.polixis.task1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static Map<String, String> monthMap = new HashMap<>();

    @PostConstruct
    private void init() {
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");
    }

    public static Instant convertToInstant (String dateString) {
        Instant convertedDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (!dateString.contains("/")) {
            dateString = normalizeDateString(dateString);
        }
        try {
            convertedDate = simpleDateFormat.parse(dateString).toInstant();
        } catch (ParseException e) {
            log.info("error in parsing date");
        }
        log.info("dateString {} converted", dateString);
        return convertedDate;
    }

    public static String normalizeDateString (String dateString) {
        String[] dateStringElements = dateString.split(" ");
        String normalizedString = "";
        normalizedString = normalizedString + dateStringElements[0].trim().replace(",", "").replaceAll("[a-z]", "") + "/";
        normalizedString = normalizedString + monthMap.get(dateStringElements[1].trim()) + "/";
        normalizedString = normalizedString + dateStringElements[2].trim();
        log.info("dateString {} normalized to {}", dateString, normalizedString);
        return normalizedString;
    }

}
