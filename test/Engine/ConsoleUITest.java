package Engine;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by Malia on 11/26/17.
 */
public class ConsoleUITest extends TestCase {

    public void testSeparateValuesByComma() {
        final String connector = ", ";
        final String natural = "Natural";
        final String lime = "lime";
        final String sparkling = "sparkling";
        final String water = "water";
        final boolean isWord = true;
        final String initialString = natural + connector + lime + connector + sparkling + connector + water;

        final ArrayList<String> testValues = new ArrayList<>();
        testValues.add(natural);
        testValues.add(lime);
        testValues.add(sparkling);
        testValues.add(water);
        final ArrayList<String> commaSeparatedValues = ConsoleUI.separateValuesByComma(initialString);

        for (int i = 0; i < testValues.size(); i++) {
            if (!testValues.get(i).equals(commaSeparatedValues.get(i))) {
                assertTrue(false);
            }
        }
        assertTrue(isWord);
    }

    public void testGetUserInputForProject() {
        ConsoleUI.getUserInputForProject();
    }
}
