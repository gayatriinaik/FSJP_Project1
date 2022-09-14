package fileHandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class performs all the file related operations.
 * 
 * @author gayatriinaik
 *
 */
public class FileHandling {

	private static Pattern extPattern = Pattern.compile("([^\\s]+(\\.(?i)(txt|xml|php|java))$)");
	private static String dirPath = new File("").getAbsolutePath();

	/**
	 * Entry point of the program. Display welcome message, check directory and
	 * display the main menu to proceed with further operations.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Company Lockers Pvt. Ltd." + "\n" + "Application Name : LockedMe.com" + "\n"
				+ "Developer Details: Gayatri Naik");
		System.out.println("This application will help you to access your files and directories easily. "
				+ "Please read the instructions carefully and enter appropriate inputs: " + "\n");
		Scanner sc = new Scanner(System.in);
		checkDirectory(sc);
		displayMenu(sc);
	}

	/**
	 * Method to check if the user wants to continue with the default directory or
	 * change the directory to perform the file related operations in the desired
	 * directory.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void checkDirectory(Scanner scanner) {
		System.out.println("Enter 'Y' if you want to perform all the file operations in the default directory "
				+ "or Enter 'N' if you want to specify a different directory");
		String value = scanner.next();
		if (value.equalsIgnoreCase("Y")) {
			dirPath = new File("").getAbsolutePath();
		} else if (value.equalsIgnoreCase("N")) {
			changeDirectory(scanner);
		} else {
			System.out.println("Please enter valid input");
			checkDirectory(scanner);
		}
	}

	/**
	 * Change the directory to perform the file related operations in the user
	 * defined directory.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void changeDirectory(Scanner scanner) {
		System.out.println("Please enter the directory path");
		File dirFile = new File(scanner.next());
		if (dirFile.exists()) {
			try {
				dirPath = dirFile.getCanonicalPath();
				System.out.println("Directory path changed to " + dirPath);
			} catch (IOException e) {
				System.out.println("Unable to change path." + e.getMessage());
			}
		} else {
			System.out.println(dirFile + " is not a directory");
			checkDirectory(scanner);
		}
	}

	/**
	 * Method to display the Main Menu and invoke appropriate methods as per the
	 * user choice.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void displayMenu(Scanner scanner) {
		System.out.println("Main Menu :" + "\n" + "Press '1' to List the files in ascending order." + "\n"
				+ "Press '2' to Perform other operations from the Sub-Menu. " + "\n"
				+ "Press '3' to Change the directory path" + "\n" + "Press '4' to Exit the application.");
		String input = scanner.next();
		if (input.equalsIgnoreCase("1")) {
			listFiles(dirPath, scanner);
		} else if (input.equalsIgnoreCase("2")) {
			System.out.println("Sub Menu: " + "\n" + "Enter 'a' to Add a new file." + "\n"
					+ "Enter 'b' to Delete an exisitng file." + "\n" + "Enter 'c' for Searching the file." + "\n"
					+ "Enter 'd' to Go back to the Main Menu." + "\n" + "Enter 'e' to Exit the application.");
			checkFileOptions(scanner);
		} else if (input.equalsIgnoreCase("3")) {
			changeDirectory(scanner);
			displayMenu(scanner);
		} else if (input.equalsIgnoreCase("4")) {
			exitApplication(scanner);
		} else {
			System.out.println("Please enter valid input.");
			displayMenu(scanner);
		}
		scanner.close();
	}

	/**
	 * Method to display the Sub-Menu.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void displaySubMenu(Scanner scanner) {
		System.out.println("Sub Menu: " + "\n" + "Enter 'a' to Add a new file." + "\n"
				+ "Enter 'b' to Delete an exisitng file." + "\n" + "Enter 'c' for Searching the file." + "\n"
				+ "Enter 'd' to Go back to the Main Menu." + "\n" + "Enter 'e' to Exit the application.");
		checkFileOptions(scanner);
	}

	/**
	 * Method to list all the files and folders from the directory.
	 * 
	 * @param directory
	 *            directory path
	 * @param scanner
	 *            input from the user
	 */
	public static void listFiles(String directory, Scanner scanner) {
		File fileDir = new File(directory);
		if (fileDir.isDirectory()) {
			List<String> listFile = Arrays.asList(fileDir.list());
			if (!listFile.isEmpty()) {
				Collections.sort(listFile, String.CASE_INSENSITIVE_ORDER);
				System.out.println("Listing the files & folders located in " + dirPath + " in ascending order:");
				for (String s : listFile) {
					System.out.println(s);
				}
			} else {
				System.out.println("Folder is empty");
			}
		} else {
			System.out.println(fileDir.getAbsolutePath() + " is not a directory");
		}
		checkAgain(scanner, false);
	}

	/**
	 * Method to exit the application.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void exitApplication(Scanner scanner) {
		scanner.close();
		System.out.println("Thank you for using the application.");
		System.exit(0);
	}

	/**
	 * Method to continue with the application and display the main menu.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void checkAgain(Scanner scanner, boolean isSubMenu) {
		System.out.println("Do you want to continue with the application? Enter 'Y' or 'N'.");
		String option = scanner.next();
		if (!isSubMenu) {
			if (option.equalsIgnoreCase("Y")) {
				displayMenu(scanner);
			} else if (option.equalsIgnoreCase("N")) {
				exitApplication(scanner);
			} else {
				System.out.println("Please enter valid input");
				checkAgain(scanner, false);
			}
		} else {
			if (option.equalsIgnoreCase("Y")) {
				displaySubMenu(scanner);
			} else if (option.equalsIgnoreCase("N")) {
				exitApplication(scanner);
			} else {
				System.out.println("Please enter valid input");
				checkAgain(scanner, true);
			}
		}
	}

	/**
	 * Method to continue with the application and display the sub menu.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void checkAgainSubMenu(Scanner scanner) {
		System.out.println("Do you want to continue with the application? Enter 'Y' or 'N'.");
		String option = scanner.next();
		if (option.equalsIgnoreCase("Y")) {
			displaySubMenu(scanner);
		} else if (option.equalsIgnoreCase("N")) {
			exitApplication(scanner);
		} else {
			System.out.println("Please enter valid input");
			checkAgainSubMenu(scanner);
		}
	}

	/**
	 * Method to check the user input and invoke appropriate methods.
	 * 
	 * @param scanner
	 *            input from the user
	 */
	public static void checkFileOptions(Scanner scanner) {
		String fileOption = scanner.next();
		if (fileOption.equalsIgnoreCase("a")) {
			addNewFile(scanner);
		} else if (fileOption.equalsIgnoreCase("b")) {
			deleteFile(scanner);
		} else if (fileOption.equalsIgnoreCase("c")) {
			searchFile(scanner);
		} else if (fileOption.equalsIgnoreCase("d")) {
			displayMenu(scanner);
		} else if (fileOption.equalsIgnoreCase("e")) {
			exitApplication(scanner);
		} else {
			System.out.println("Please enter valid input.");
			displaySubMenu(scanner);
		}
	}

	/**
	 * Method to add a new file. Check the file extension, if it is a valid
	 * extension (txt, php, xml or java) then check if file already exists in the
	 * directory or not. If it exists then display the message that 'File already
	 * exists', else create a new file and add default text in the file : "File
	 * created by LockedMe application on {}" with current date time. If the
	 * extension is invalid then display the message. If the
	 * 
	 * @param scanner
	 */
	public static void addNewFile(Scanner scanner) {
		System.out.println("Please enter the file name along with the extension. Eg: demo.txt");
		File file = new File(dirPath, scanner.next());
		try {
			if (validateFileExtn(file.getName())) {
				boolean value = file.createNewFile();
				if (value) {
					System.out.println("New File " + file.getName() + " is created.");
					FileWriter fw = new FileWriter(file);
					fw.write("File created by LockedMe application on " + LocalDateTime.now());
					fw.close();
				} else {
					System.out.println("The file already exists.");
				}
			} else {
				System.out.println("Please check the extension of the file. " + "\n"
						+ "Currently we support only 'txt','xml','java' and 'php' files. " + "\n"
						+ "All other extensions will be supported in the next version.");
			}
		} catch (Exception e) {
			System.out.println("Unable to create a new file. " + e.getMessage());
		}
		checkAgain(scanner, true);
	}

	/**
	 * Method to delete the file specified by the user. The file name is case
	 * sensitive.
	 * 
	 * @param scanner
	 *            file name
	 */
	public static void deleteFile(Scanner scanner) {
		System.out.println("Please enter the file name");
		File file = new File(dirPath, scanner.next());
		try {
			if (file.getCanonicalPath().equals(file.getAbsolutePath())) {
				if (file.delete()) {
					System.out.println(file.getName() + " deleted successfully.");
				} else {
					System.out.println("File " + file.getName() + " not found");
				}
			} else {
				System.out.println("File " + file.getName() + " not found");
			}
		} catch (IOException e) {
			System.out.println("Unable to delete the file." + e.getMessage());
		}
		checkAgain(scanner, true);
	}

	/**
	 * Method to search the file specified by the user. The file name is case
	 * sensitive.
	 * 
	 * @param scanner
	 *            file name
	 */
	public static void searchFile(Scanner scanner) {
		System.out.println("Enter the file name");
		File file = new File(dirPath, scanner.next());
		try {
			if (file.getCanonicalPath().equals(file.getAbsolutePath()) && file.exists()) {
				System.out.println("File " + file.getName() + " exists");
			} else {
				System.out.println("File " + file.getName() + " does not exist");
			}
		} catch (IOException e) {
			System.out.println("Unable to search the file." + e.getMessage());
		}
		checkAgain(scanner, true);
	}

	/**
	 * Method to validate the file extension before adding a new file.
	 * 
	 * @param fileName
	 *            name of the file
	 * @return true if the file extension given by the user is txt, xml, php or java
	 *         else return false.
	 */
	public static boolean validateFileExtn(String fileName) {
		Matcher mtch = extPattern.matcher(fileName);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}
}