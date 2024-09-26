package org.translation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JSONTranslatorTest {

    JSONTranslator jsonTranslator = new JSONTranslator();

    @Test
    public void getCountryLanguages() { // counts how many translations there are for country name
       List<String> countryLanguages = jsonTranslator.getCountryLanguages("can");
       assertEquals("There should be 35 languages but got " + countryLanguages.size(), 35, countryLanguages.size());
    }

    @Test
    public void getCountries() { // counts total number of countries on sample.json
        List<String> languages = jsonTranslator.getCountries();
        assertEquals("There should be 193 countries but got " + languages.size(), 193, languages.size());

    }

    @Test
    public void translate() {
        assertEquals("Canada", jsonTranslator.translate("can", "en"));
    }
}
