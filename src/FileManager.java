import java.io.*;

public class FileManager {

    private final String DIRECTORY = "files/";

    // Create File
    public void createFile(String fileName) {

        try {

            File file = new File(DIRECTORY + fileName);

            if (file.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            System.out.println("Error creating file.");
            e.printStackTrace();
        }
    }

    // Write to File
    public void writeFile(String fileName, String content) {

        try {

            FileWriter writer = new FileWriter(DIRECTORY + fileName);

            writer.write(content);

            writer.close();

            System.out.println("Data written successfully.");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
    }

    // Read File
    public void readFile(String fileName) {

        try {

            File file = new File(DIRECTORY + fileName);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            System.out.println("\n===== FILE CONTENT =====");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }

    // Modify File (Append Content)
    public void modifyFile(String fileName, String newContent) {

        try {

            FileWriter writer = new FileWriter(DIRECTORY + fileName, true);

            writer.write("\n" + newContent);

            writer.close();

            System.out.println("File modified successfully.");

        } catch (IOException e) {
            System.out.println("Error modifying file.");
            e.printStackTrace();
        }
    }

    // Delete File
    public void deleteFile(String fileName) {

        File file = new File(DIRECTORY + fileName);

        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Unable to delete file.");
        }
    }
}