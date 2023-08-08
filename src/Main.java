import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class Main {
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";


        try
        {

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typiacally, we want the user to pick the file so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.
            chooser.setCurrentDirectory(workingDirectory);
            // Using the chooser adds some complexity to the code.
            // we have to code the complete program within the conditional return of
            // the filechooser because the user can close it without picking a file

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Finally we can read the file LOL!
                int line = 0;
                int words = 0;
                int characters = 0;
                String[] wordsOnLine;

                while(reader.ready())
                {
                    rec = reader.readLine();
                    line++;
                    wordsOnLine = rec.split(" ");
                    words = wordsOnLine.length + words;
                    characters = rec.length() + characters;


                }
                System.out.println("File Name: " + file.getFileName());
                System.out.println("Number of lines: " + line);
                System.out.println("Number of words: "+ words);
                System.out.println("Number of characters: " + characters);

                reader.close(); // must close the file to seal it and flush buffer
            }
            else  // user closed the file dialog wihtout choosing
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }  // end of TRY
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}