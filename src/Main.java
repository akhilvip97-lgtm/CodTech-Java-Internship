import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FileManager fileManager = new FileManager();

        int choice;

        do {
            System.out.println("\n===== FILE HANDLING UTILITY =====");
            System.out.println("1. Create File");
            System.out.println("2. Write to File");
            System.out.println("3. Read File");
            System.out.println("4. Modify File");
            System.out.println("5. Delete File");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter file name: ");
                    String createFile = sc.nextLine();
                    fileManager.createFile(createFile);
                    break;

                case 2:
                    System.out.print("Enter file name: ");
                    String writeFile = sc.nextLine();

                    System.out.print("Enter content: ");
                    String content = sc.nextLine();

                    fileManager.writeFile(writeFile, content);
                    break;

                case 3:
                    System.out.print("Enter file name: ");
                    String readFile = sc.nextLine();

                    fileManager.readFile(readFile);
                    break;

                case 4:
                    System.out.print("Enter file name: ");
                    String modifyFile = sc.nextLine();

                    System.out.print("Enter new content to append: ");
                    String modifyContent = sc.nextLine();

                    fileManager.modifyFile(modifyFile, modifyContent);
                    break;

                case 5:
                    System.out.print("Enter file name: ");
                    String deleteFile = sc.nextLine();

                    fileManager.deleteFile(deleteFile);
                    break;

                case 6:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}