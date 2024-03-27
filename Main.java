import java.io.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String username = null;
        String password = null;


        while (true) {
            if (!loggedIn) {
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m╔═════════════════════════==╗\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m║Welcome to our application!║\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m╠═════════════════════════==╣\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m║Please choose an option:   ║\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m║    1. Login               ║\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m║    2. Sign Up             ║\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m║    0. Exit                ║\u001B[0m");
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\u001B[32m╚═════════════════════════==╝\u001B[0m");


                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 0:
                        System.out.println("\u001B[35mExiting program...\u001B[0m");
                        return;
                    case 1:
                        System.out.println("\u001B[34mLogin\u001B[0m");
                        System.out.print("\u001B[32mEnter your username:\u001B[0m");
                        String loginUsername = scanner.nextLine();

                        System.out.print("\u001B[32mEnter your password:\u001B[0m");
                        String loginPassword = scanner.nextLine();

                        if (userFileExists(loginUsername) && passwordMatches(loginUsername, loginPassword)) {
                            System.out.println("\u001B[35mLogin successful!\u001B[0m");
                            loggedIn = true;
                        } else {
                            System.out.println("\u001B[31mInvalid username or password. Login failed.\u001B[0m");
                        }
                        break;
                        case 2:
                        Scanner Signup_input = new Scanner(System.in);
                        System.out.println("\u001B[34mNew signup\u001B[0m");
                        boolean usernameExists = true;
                        while (usernameExists) {
                            System.out.print("\u001B[32mEnter Username:\u001B[32m ");
                            String usernameSignup = Signup_input.nextLine();
                            String userFile = usernameSignup + ".txt";
                            try{
                                File myFile = new File(userFile);
                                FileWriter fileWriter= new FileWriter(userFile);

                                try
                                {
                                    myFile.createNewFile();
                                    usernameExists = false; // exit loop when unique username is found
                                } catch (IOException e)
                                {
                                    System.out.println(
                                            "\u001B[31mCannot create the file at the moment\u001B[0m.\u001B[31m \n please try again later!!!\u001B[0m"
                                    );
                                }

                                System.out.print("\033[32mEnter password:\033[0m ");
                                String password1= Signup_input.nextLine();

                                fileWriter.write("\npassword: "+password1+" ");
                                fileWriter.close();
                            }catch(IOException e){
                                System.out.println("\u001B[31merror occured\u001B[0m");

                            }

                            System.out.println("\u001B[35mAccount Created Successfully!\u001B[0m");

                        }
                        break;
                    default:
                        System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
                        break;
                }
            } else {
                System.out.println("\u001B[34mPlease choose an option\u001B[0m");
                System.out.println("\u001B[37m1. Generate Passwords\u001B[0m");
                System.out.println("\u001B[37m2. Password strength checker\u001B[0m");
                System.out.println("\u001B[37m3. Manage Passwords\u001B[0m");
                System.out.println("\u001B[37m0. Logout\u001B[0m");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 0:
                        System.out.println("Logging out...");
                        loggedIn = false;
                        break;
                    case 1:
                        PasswordGenerator.main(args);// pehle toh chalra thana??
                        break;
                    case 2:
                        PasswordStrengthChecker.main(args);
                        break;
                    case 3:
                        PasswordManager.main(args);
                        break;
                    default:
                        System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
                        break;
                }
            }}}
    private static boolean userFileExists(String username) {
        String userFile = username + ".txt";
        File file = new File(userFile);
        return file.exists();
    }

    private static boolean passwordMatches(String username, String password) {
        String userFile = username + ".txt";
        try (Scanner fileScanner = new Scanner(new File(userFile))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("password:")) {
                    String storedPassword = line.substring(9).trim();
                    return storedPassword.equals(password);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}

class PasswordGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User preferences
        boolean useUppercase = getUserPreference("\t\t\t\t\u001B[32mDo you want to use uppercase letters? \u001B[34m(Yes/No): ", scanner);
        boolean useLowercase = getUserPreference("\t\t\t\t\u001B[32mDo you want to use lowercase letters? \u001B[34m(Yes/No): ", scanner);
        boolean useNumbers = getUserPreference("\t\t\t\t\u001B[32mDo you want to use numbers? \u001B[34m(Yes/No): ", scanner);
        boolean useSymbols = getUserPreference("\t\t\t\t\u001B[32mDo you want to use symbols? \u001B[34m(Yes/No): ", scanner);

        // Password length and number of passwords
        int passwordLength = getPasswordLength(scanner);
        int numberOfPasswords = getNumberOfPasswords(scanner);

        // Generate passwords
        String[] passwords = generateRandomPasswords(numberOfPasswords, passwordLength, useUppercase, useLowercase, useNumbers, useSymbols);

        // Print passwords and their strengths
        System.out.println("\u001B[0m\u001B[34mGenerated Passwords:\u001B[0m");
        for (int i = 0; i < passwords.length; i++) {
            printPasswordWithStrength(i + 1, passwords[i]);
        }

        // Save passwords to a file
        try {
            savePasswordsToFile(passwords);
        } catch (IOException e) {
            System.out.println("\u001B[31mAn error occurred while saving passwords to file.\u001B[0m");
        }

        // Allow user to choose a password and save it to another file
        String chosenPassword = choosePassword(scanner, passwords);
        try {
            saveChosenPasswordToFile(chosenPassword);
            System.out.println("\u001B[32mChosen password saved to file successfully!\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31mAn error occurred while saving the chosen password to file.\u001B[0m");
        }
    }

    // Method to get user's preference (Yes/No)
    private static boolean getUserPreference(String message, Scanner scanner) {
        System.out.print(message);
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("Yes");
    }

    // Method to get password length from user
    private static int getPasswordLength(Scanner scanner) {
        int passwordLength = 0;
        boolean validLength = false;
        while (!validLength) {
            try {
                System.out.print("\u001B[0m\u001B[35mEnter the desired length of the password: \u001B[0m");
                passwordLength = scanner.nextInt();
                scanner.nextLine(); // Consume the remaining newline character
                validLength = true;
            } catch (Exception e) {
                System.out.println("\u001B[31mInvalid input. Please enter a valid length.\u001B[0m");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return passwordLength;
    }

    // Method to get the number of passwords from user
    private static int getNumberOfPasswords(Scanner scanner) {
        int numberOfPasswords = 0;
        boolean validNumber = false;
        while (!validNumber) {
            try {
                System.out.print("\u001B[0m\u001B[35mEnter the number of passwords to generate: \u001B[0m");
                numberOfPasswords = scanner.nextInt();
                scanner.nextLine(); // Consume the remaining newline character
                validNumber = true;
            } catch (Exception e) {
                System.out.println("\u001B[31mInvalid input. Please enter a valid number.\u001B[0m");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return numberOfPasswords;
    }

    // Method to generate random passwords
    private static String[] generateRandomPasswords(int numberOfPasswords, int length, boolean useUppercase, boolean useLowercase,
                                                    boolean useNumbers, boolean useSymbols) {
        String[] passwords = new String[numberOfPasswords];
        for (int i = 0; i < numberOfPasswords; i++) {
            passwords[i] = generateRandomPassword(length, useUppercase, useLowercase, useNumbers, useSymbols);
        }
        return passwords;
    }

    // Method to generate a random password
    private static String generateRandomPassword(int length, boolean useUppercase, boolean useLowercase,
                                                 boolean useNumbers, boolean useSymbols) {
        StringBuilder password = new StringBuilder();
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()_-+=<>?";

        String characters = "";
        if (useUppercase) {
            characters += uppercaseLetters;
        }
        if (useLowercase) {
            characters += lowercaseLetters;
        }
        if (useNumbers) {
            characters += numbers;
        }
        if (useSymbols) {
            characters += symbols;
        }

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }

    // Method to print password with strength
    private static void printPasswordWithStrength(int passwordNumber, String password) {
        int strength = calculatePasswordStrength(password);
        System.out.println("\u001B[32mPassword " + passwordNumber + ":\u001B[0m " + password);
        System.out.println("\u001B[32mStrength:\u001B[0m " + strength + "/10");
        System.out.println();
    }

    // Method to calculate password strength
    private static int calculatePasswordStrength(String password) {
        int strength = 0;
        if (password.matches(".*[A-Z].*")) {
            strength += 2; // Contains uppercase letter
        }
        if (password.matches(".*[a-z].*")) {
            strength += 2; // Contains lowercase letter
        }
        if (password.matches(".*\\d.*")) {
            strength += 2; // Contains number
        }
        if (password.matches(".*[!@#\\$%\\^&\\*\\(\\)_\\-\\+=<>\\?].*")) {
            strength += 2; // Contains symbol
        }
        if (password.length() >= 8) {
            strength += 2; // Length is at least 8 characters
        }
        return strength;
    }

    // Method to save passwords to a file
    private static void savePasswordsToFile(String[] passwords) throws IOException {
        FileWriter fileWriter = new FileWriter("passwords.txt");
        fileWriter.write("Generated Passwords:" + "\n");
        for (int i = 0; i < passwords.length; i++) {
            fileWriter.write((i + 1) + ". " + passwords[i] + "\n");
        }
        fileWriter.close();
    }

    // Method to allow user to choose a password
    private static String choosePassword(Scanner scanner, String[] passwords) {
        System.out.println("\n\u001B[35mChoose a password (1 to " + passwords.length + "):\u001B[0m");

        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            try {
                System.out.print("\u001B[34mEnter your choice:\u001B[0m ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the remaining newline character
                if (choice >= 1 && choice <= passwords.length) {
                    validChoice = true;
                } else {
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid choice (1 to " + passwords.length + ").\u001B[0m");
                }
            } catch (Exception e) {
                System.out.println("\u001B[31mInvalid input. Please enter a valid choice.\u001B[0m");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return passwords[choice - 1];
    }

    // Method to save the chosen password to a file
    private static void saveChosenPasswordToFile(String chosenPassword) throws IOException {
        FileWriter fileWriter = new FileWriter("chosen_password.txt");
        fileWriter.write("Chosen Password: " + chosenPassword + "");
        fileWriter.close();
    }
}

class PasswordStrengthChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34mEnter the password to check its strength:\u001B[0m");
        String password = scanner.nextLine();

        int score = calculatePasswordStrength(password);

        System.out.println("\u001B[32mPassword Strength:\u001B[0m " + getPasswordStrengthLabel(score));
    }

    private static int calculatePasswordStrength(String password) {
        int score = 0;

        if (password.length() >= 8)
            score++;
        if (password.matches("(?=.*[a-z]).*"))
            score++;
        if (password.matches("(?=.*[A-Z]).*"))
            score++;
        if (password.matches("(?=.*\\d).*"))
            score++;
        if (password.matches("(?=.*[!@#$%^&*()\\-_+=<>?]).*"))
            score++;

        return score;
    }

    private static String getPasswordStrengthLabel(int score) {
        if (score >= 4)
            return "Strong";
        else if (score >= 3)
            return "Medium";
        else if (score >= 2)
            return "Weak";
        else
            return "Very Weak";
    }

}
class PasswordManager {
    private static final String FILE_NAME = "passwords.txt";
    private static List<String> passwords = new ArrayList<String>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\u001B[35m Choose an option: \u001B[0m");
            System.out.println("\u001B[34m1. Add a new password\u001B[0m");
            System.out.println("\u001B[34m2. View passwords\u001B[0m");
            System.out.println("\u001B[34m3. Clear all passwords\u001B[0m");
            System.out.println("\u001B[34m0. Exit\u001B[0m");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 0:
                    System.out.println("\u001B[32mExiting password manager...\u001B[0m");
                    return;
                case 1:
                    addNewPassword(scanner);
                    break;
                case 2:
                    viewPasswords(scanner);
                    break;
                case 3:
                    clearAllPasswords();
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[35m");
                    break;
            }
        }
    }

    private static void addNewPassword(Scanner scanner) {
        while (true) {
            System.out.print("\u001B[35mEnter the site or type 'return' to go back :\u001B[0m");
            String site = scanner.nextLine();

            if (site.equalsIgnoreCase("return")) {
                break;
            }

            System.out.print("\u001B[34mEnter the username:\u001B[0m");
            String username = scanner.nextLine();

            System.out.print("\u001B[34mEnter the password:\u001B[0m");
            String password = scanner.nextLine();

            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
                writer.println(site);
                writer.println("username: " + username);
                writer.println("password: " + password);
                writer.println();

                passwords.add(site);
                System.out.println("\u001B[32mPassword added successfully!\u001B[0m");
            } catch (IOException e) {
                System.out.println("\u001B[31mAn error occurred while writing to the file.\u001B[0m");
            }
        }
    }

    private static void viewPasswords(Scanner scanner) {
        while (true) {
            System.out.print("\u001B[35mEnter the site name or type 'return' to go back or 'all' to view all passwords : \u001B[0m");
            String site = scanner.nextLine();

            if (site.equalsIgnoreCase("return")) {
                break;
            }

            try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
                boolean passwordFound = false;

                if (site.equalsIgnoreCase("all")) {
                    if (!fileScanner.hasNext()) {
                        System.out.println("No saved Password");
                    } else {
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();

                            if (line.isEmpty()) {
                                System.out.println();
                            } else {
                                System.out.println(line);
                            }
                        }
                    }

                    passwordFound = true;
                } else {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();

                        if (line.equalsIgnoreCase(site)) {
                            passwordFound = true;
                            System.out.println("Site: " + line);
                            System.out.println(fileScanner.nextLine()); // username
                            System.out.println(fileScanner.nextLine()); // password
                            System.out.println();
                            break;
                        }
                    }
                }

                if (!passwordFound) {
                    System.out.println("\u001B[31mPassword not available for " + site + ".\u001B[0m");
                }
            } catch (FileNotFoundException e) {
                System.out.println("\u001B[31mPassword file not found.\u001B[0m");
            }
        }

    }

    private static void clearAllPasswords() {
        if (passwords.isEmpty()) {
            System.out.println("No passwords saved.");
            return;
        }

        System.out.println("\u001B[32mAre you sure you want to clear all passwords? (Y/N)\u001B[0m");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            passwords.clear();
            System.out.println("\u001B[33mAll passwords cleared.\u001B[0m");

            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                // Write an empty string to clear the file content
                writer.print("");
                System.out.println("\u001B[33mPasswords removed from the file.\u001B[0m");
            } catch (IOException e) {
                System.out.println("\u001B[31mAn error occurred while writing to the file.\u001B[0m");
            }
        } else {
            System.out.println("Clear operation canceled.");
        }
    }
}
