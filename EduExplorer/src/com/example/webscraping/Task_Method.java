package com.example.webscraping;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;


public class Task_Method {
    // Calculate frequencies of course names



    public static Map<String, Integer> calculateCourseNameFrequencies(List<String[]> csvData) {
        Map<String, Integer> courseNameFrequencies = new HashMap<>();

        // Skip the header row (first row)
        for (int i = 1; i < csvData.size(); i++) { // Start from the second row
            String[] row = csvData.get(i);
            if (row.length > 1) {
                String courseName = row[1].trim().toLowerCase(); // Convert to lowercase and trim
                // Skip empty or header-like rows
                if (!courseName.equalsIgnoreCase("Course Title")) {
                    courseNameFrequencies.put(courseName, courseNameFrequencies.getOrDefault(courseName, 0) + 1);
                }
            }
        }
        return courseNameFrequencies;
    }
    public static void searchbyhours(String hoursRange, Boolean NoResult, String filePath,String fulldetails)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split the line by commas

                // Make sure the row has at least 9 columns (index 8 for the Price column)
                if (columns.length >= 8) {
                    // Trim spaces and check if the price matches
                    String hoursInCsv = columns[3].trim();
                    //System.out.println("priceInCsv :" + priceInCsv);
                    if (hoursInCsv.equals(hoursRange)) {
                        // Print relevant details from the row
                        System.out.println("Source: " + columns[0].trim());
                        System.out.println("Course Title: " + columns[1].trim());
                        System.out.println("Course URL: " + columns[2].trim());
                        System.out.println("Duration: " + columns[3].trim());
                        System.out.println("Views: " + columns[4].trim());
                        System.out.println("Creator Info: " + columns[5].trim());
                        System.out.println("Email: " + columns[6].trim());
                        System.out.println("Date: " + columns[7].trim());
                        System.out.println("Price: " + columns[8].trim());
                        System.out.println();  // New line for separation between entries
                        fulldetails = columns[0].trim() + "\n" +columns[1].trim() + "\n"+ columns[2].trim() + "\n"+ columns[3].trim() + "\n"+columns[4].trim() + "\n"+columns[5].trim() + "\n"+ columns[6].trim()
                                + "\n"+ columns[7].trim() + "\n"+ columns[8].trim();
                        NoResult = false;
                    }
                }
            }
            if(NoResult == true)
            {
                System.out.println("No Result found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchbyprice(String priceRange, Boolean NoResult, String filePath, String fulldetails){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //String fulldetails = "";
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");  // Split the line by commas

                // Make sure the row has at least 9 columns (index 8 for the Price column)
                if (columns.length >= 8) {
                    // Trim spaces and check if the price matches
                    String priceInCsv = columns[8].trim();
                    //System.out.println("priceInCsv :" + priceInCsv);
                    if (priceInCsv.equals(priceRange)) {
                        // Print relevant details from the row
                        System.out.println("Source: " + columns[0].trim());
                        System.out.println("Course Title: " + columns[1].trim());
                        System.out.println("Course URL: " + columns[2].trim());
                        System.out.println("Duration: " + columns[3].trim());
                        System.out.println("Views: " + columns[4].trim());
                        System.out.println("Creator Info: " + columns[5].trim());
                        System.out.println("Email: " + columns[6].trim());
                        System.out.println("Date: " + columns[7].trim());
                        System.out.println("Price: " + columns[8].trim());
                        //System.out.println();  // New line for separation between entries
                        fulldetails = "Source: " + columns[0].trim() +"Course Title: "+ columns[1].trim() + "Course URL: " +columns[2].trim() + "Duration: " +columns[3].trim() +"Views: " +columns[4].trim() +"Creator Info: " +columns[5].trim() + "Email: " +columns[6].trim()
                                + "Date: " +columns[7].trim() + "Price: " +columns[8].trim();
                       // NoResult = false;
                    }

                }
            }
            if(NoResult == true)
            {
                System.out.println("No Result found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Display the most frequent course names
    /*public static void displayTopCourses(Map<String, Integer> courseNameFrequencies, int count) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of top words to display: ");
        int topCount = count;
        System.out.println("topCount :" + topCount);

        String name;
        String number;
        try {
            //topCount = scanner.nextInt();
            // Sort courses by frequency
            courseNameFrequencies.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Sort descending by frequency
                    .limit(topCount) // Get top N
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number.");
            return;  // Exit the program or handle error appropriately
        }

    }*/

    // Method to return the top N courses by frequency
    public static List<String> displayTopCourses(Map<String, Integer> courseNameFrequencies, int topCount) {
        List<String> topCourses = new ArrayList<>();

        try {
            // Sort courses by frequency and collect the top N
            topCourses = courseNameFrequencies.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Sort descending by frequency
                    .limit(topCount) // Get top N
                    .map(entry -> entry.getKey() + ": " + entry.getValue()) // Format each entry as a string
                    .collect(Collectors.toList()); // Collect into a list

        } catch (Exception e) {
            System.out.println("Error: Please enter a valid number.");
        }

        return topCourses; // Return the list of formatted top courses
    }

    // Search for frequency of a specific course name
    public static void searchCourseNameFrequency(Map<String, Integer> courseNameFrequencies) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course name to search for: ");
        String searchCourse = scanner.nextLine().trim().toLowerCase(); // Normalize the search input

        Integer frequency = courseNameFrequencies.get(searchCourse);

        if (frequency != null) {
            System.out.println("Frequency of '" + searchCourse + "': " + frequency);
        } else {
            System.out.println("Course name '" + searchCourse + "' not found.");
        }
    }



    // Helper method for valid input
    public static int getValidChoice(Scanner scanner, int min, int max) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    scanner.nextLine(); // Consume newline

                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }

    public static Set<String> buildVocabulary(List<String[]> csvData) {
        Set<String> vocabulary = new HashSet<>();
        for (String[] row : csvData) {
            for (String word : row[1].split(" ")) {
                vocabulary.add(word.toLowerCase());
            }
        }
        return vocabulary;
    }

    public static boolean isWordInVocabulary(String word, Set<String> vocabulary) {
        return vocabulary.contains(word.toLowerCase());
    }

    public static List<String> getClosestWords(String input, Set<String> vocabulary) {
        List<String> closestWords = new ArrayList<>();
        int threshold = 2;
        for (String word : vocabulary) {
            if (calculateEditDistance(input.toLowerCase(), word) <= threshold) {
                closestWords.add(word);
            }
        }
        return closestWords;
    }

    public static int calculateEditDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
        return dp[a.length()][b.length()];
    }

    public static List<String> searchDatabase(String input, List<String[]> csvData) {
        List<String> results = new ArrayList<>();
        for (String[] row : csvData) {
            if (row[1].toLowerCase().contains(input.toLowerCase())) {
                results.add("Course: " + row[1] + ", Website: " + row[0] + ", URL: " + row[2]);
            }
        }
        return results;
    }

    // Method to load CSV data into memory
    public static List<String[]> loadCSVData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}






