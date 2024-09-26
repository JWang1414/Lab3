package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private static final int EXPECTED_PARTS = 4;
    private static final int COUNTRY_INDEX = 0;
    private static final int ALPHA2_INDEX = 1;
    private static final int ALPHA3_INDEX = 2;
    private static final int NUMERIC_INDEX = 3;
    private final Map<String, String> alpha2CodeMap;
    private final Map<String, String> alpha3CodeMap;
    private final Map<String, String> numericCodeMap;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     *
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        alpha2CodeMap = new HashMap<>();
        alpha3CodeMap = new HashMap<>();
        numericCodeMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();

            // Skip the first line (headers)
            if (iterator.hasNext()) {
                iterator.next();
            }

            // Process each remaining line
            while (iterator.hasNext()) {
                String line = iterator.next();

                // Split the line by tabs (Country, Alpha-2 code, Alpha-3 code, Numeric)
                String[] parts = line.split("\t");

                // Use the constant EXPECTED_PARTS instead of hardcoded '4'
                if (parts.length == EXPECTED_PARTS) {
                    String country = parts[COUNTRY_INDEX];
                    String alpha2Code = parts[ALPHA2_INDEX];
                    String alpha3Code = parts[ALPHA3_INDEX];
                    String numericCode = parts[NUMERIC_INDEX];
                    // Populate the maps
                    alpha2CodeMap.put(alpha2Code, country);
                    alpha3CodeMap.put(alpha3Code, country);
                    numericCodeMap.put(numericCode, country);
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        String result = code;

        // Check if the code is in the Alpha-2 map
        if (alpha2CodeMap.containsKey(code)) {
            result = alpha2CodeMap.get(code);
        }
        // Check if the code is in the Alpha-3 map
        else if (alpha3CodeMap.containsKey(code)) {
            result = alpha3CodeMap.get(code);
        }
        // Check if the code is in the Numeric map
        else if (numericCodeMap.containsKey(code)) {
            result = numericCodeMap.get(code);
        }

        // Return the result
        return result;
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        String result = language;

        // Check if the language is in the Alpha-2 map
        if (alpha2CodeMap.containsValue(language)) {
            for (Map.Entry<String, String> entry : alpha2CodeMap.entrySet()) {
                if (entry.getValue().equals(language)) {
                    result = entry.getKey();
                    break;
                }
            }
        }

        else if (alpha3CodeMap.containsValue(language)) {
            for (Map.Entry<String, String> entry : alpha3CodeMap.entrySet()) {
                if (entry.getValue().equals(language)) {
                    result = entry.getKey();
                    break;
                }
            }
        }

        else if (numericCodeMap.containsValue(language)) {
            for (Map.Entry<String, String> entry : numericCodeMap.entrySet()) {
                if (entry.getValue().equals(language)) {
                    result = entry.getKey();
                    break;
                }
            }
        }
        return result;
    }
    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */

    public int getNumLanguages() {
        return alpha2CodeMap.size() + alpha3CodeMap.size() + numericCodeMap.size();
    }
}

