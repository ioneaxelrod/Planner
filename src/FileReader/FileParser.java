package FileReader;

import java.io.*;

/**
 * Created by Malia on 11/25/17.
 */
public class FileParser {

    public static String getPassword() {
        String fileName = "/Users/Malia/IdeaProjects/test/src/resources/password.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if((line = bufferedReader.readLine()) != null) {
                bufferedReader.close();
                return line;
            } else {
                bufferedReader.close();
                throw new IOException();
            }
            // Always close files.
        }
        catch(FileNotFoundException exc) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
             exc.printStackTrace();
        }
        return null;
    }

}
