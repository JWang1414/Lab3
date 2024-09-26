package org.translation;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static final CountryCodeConverter COUNTRY_CODE_CONVERTER = new CountryCodeConverter();
    private static final LanguageCodeConverter LANGUAGE_CODE_CONVERTER = new LanguageCodeConverter();

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator("sample.json");
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        final String quit = "quit";
        while (true) {
            String country = promptForCountry(translator);
            if (country.equals(quit)) {
                break;
            }
            String countryCode = COUNTRY_CODE_CONVERTER.fromCountry(country);
            String language = promptForLanguage(translator, countryCode);
            if (language.equals(quit)) {
                break;
            }

            String lcode = LANGUAGE_CODE_CONVERTER.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, lcode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();

        List<String> countryNames = countries.stream()
                .map(COUNTRY_CODE_CONVERTER::fromCountryCode)
                .sorted()
                .collect(Collectors.toList());

        for (String country : countryNames) {
            System.out.println(country);
        }
        System.out.println("select a country from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);

        List<String> languageNames = languages.stream()
                .map(LANGUAGE_CODE_CONVERTER::fromLanguageCode)
                .sorted()
                .collect(Collectors.toList());

        for (String language : languageNames) {
            System.out.println(language);
        }
        System.out.println("select a language from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
