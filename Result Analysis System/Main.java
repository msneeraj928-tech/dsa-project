import java.io.*;
import java.util.*;
 
public class Main {
    static class Student {
        private final String name;
        private final int m1, m2, m3;
 
        Student(String name, int m1, int m2, int m3) {
            this.name = name;
            this.m1 = m1;
            this.m2 = m2;
            this.m3 = m3;
        }
 
        String getName() {
            return name;
        }
 
        int getTotal() {
            return m1 + m2 + m3;
        }
 
        double getPercentage() {
            return getTotal() / 3.0;
        }
 
        String getGrade() {
            double percentage = getPercentage();
            if (percentage >= 90) return "A+";
            if (percentage >= 75) return "A";
            if (percentage >= 60) return "B";
            if (percentage >= 40) return "C";
            return "F";
        }
 
        String toFileLine() {
            return name + "|" + m1 + "|" + m2 + "|" + m3;
        }
 
        String summary() {
            return name + " | Total: " + getTotal() + " | "
                    + String.format(Locale.US, "%.1f", getPercentage())
                    + "% | Grade " + getGrade();
        }
    }
 
    private static final String FILE_NAME = "results.txt";
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final HashMap<String, Student> lookup = new HashMap<>();
 
    static boolean validMarks(int a, int b, int c) {
        return a >= 0 && a <= 100 && b >= 0 && b <= 100
                && c >= 0 && c <= 100;
    }
 
    static String nameKey(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }
 
    static void addResult(String name, int a, int b, int c) {
        Student student = new Student(name, a, b, c);
        students.add(student);
        lookup.put(nameKey(name), student);
    }
 
    static void searchByName(String name) {
        Student student = lookup.get(nameKey(name));
        if (student == null) {
            System.out.println("Student " + name.trim() + " not found.");
        } else {
            System.out.println(student.summary());
        }
    }
 
    static void showRankList() {
        if (students.isEmpty()) {
            System.out.println("No results available.");
            return;
        }
 
        ArrayList<Student> ranked = new ArrayList<>(students);
        ranked.sort(Comparator.comparingInt(Student::getTotal).reversed()
                .thenComparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
 
        System.out.println("--- RANK LIST ---");
        int rank = 1;
        for (Student student : ranked) {
            System.out.println(rank++ + ". " + student.getName() + " | "
                    + student.getTotal() + " | "
                    + String.format(Locale.US, "%.1f", student.getPercentage()) + "%");
        }
    }
 
    static void showTopperAndAverage() {
        if (students.isEmpty()) {
            System.out.println("No results available.");
            return;
        }
 
        int max = Integer.MIN_VALUE;
        double sumPercentage = 0;
        for (Student student : students) {
            max = Math.max(max, student.getTotal());
            sumPercentage += student.getPercentage();
        }
 
        for (Student student : students) {
            if (student.getTotal() == max) {
                System.out.println("Topper: " + student.getName()
                        + " (" + student.getTotal() + ")");
            }
        }
        System.out.println("Class average: "
                + String.format(Locale.US, "%.1f", sumPercentage / students.size()) + "%");
    }
 
    static void loadResults() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
 
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;
 
                try {
                    String name = parts[0].trim();
                    int a = Integer.parseInt(parts[1].trim());
                    int b = Integer.parseInt(parts[2].trim());
                    int c = Integer.parseInt(parts[3].trim());
 
                    if (!name.isEmpty() && validMarks(a, b, c)
                            && !lookup.containsKey(nameKey(name))) {
                        addResult(name, a, b, c);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + FILE_NAME + ": " + e.getMessage());
        }
    }
 
    static void saveResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save " + FILE_NAME + ": " + e.getMessage());
        }
    }
 
    static void printMenu() {
        System.out.println("====== Result Analysis System ======");
        System.out.println("1. Add Result");
        System.out.println("2. Search Result by Name");
        System.out.println("3. Rank List");
        System.out.println("4. Topper & Class Average");
        System.out.println("5. Exit");
        System.out.println("--------------------------------------------");
    }
 
    static void handleAdd(Scanner sc) {
        System.out.print("Enter student name: ");
        if (!sc.hasNextLine()) return;
        String name = sc.nextLine().trim();
 
        if (name.isEmpty()) {
            System.out.println("Name cannot be blank.");
            return;
        }
        if (lookup.containsKey(nameKey(name))) {
            System.out.println("Result for " + name + " already exists.");
            return;
        }
 
        System.out.print("Enter marks of 3 subjects (comma separated): ");
        if (!sc.hasNextLine()) return;
        String[] parts = sc.nextLine().trim().split("\\s*[, ]\\s*");
        if (parts.length != 3) {
            System.out.println("Invalid input. Enter exactly 3 marks separated by commas.");
            return;
        }
 
        int[] marks = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                marks[i] = Integer.parseInt(parts[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Marks must be whole numbers.");
            return;
        }
 
        if (!validMarks(marks[0], marks[1], marks[2])) {
            System.out.println("Invalid marks. Enter marks between 0 and 100.");
            return;
        }
 
        addResult(name, marks[0], marks[1], marks[2]);
        System.out.println("Result added. " + lookup.get(nameKey(name)).summary());
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadResults();
 
        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            if (!sc.hasNextLine()) break;
            String choice = sc.nextLine().trim();
 
            switch (choice) {
                case "1":
                    handleAdd(sc);
                    break;
                case "2":
                    System.out.print("Enter student name: ");
                    if (sc.hasNextLine()) searchByName(sc.nextLine());
                    break;
                case "3":
                    showRankList();
                    break;
                case "4":
                    showTopperAndAverage();
                    break;
                case "5":
                    saveResults();
                    System.out.println("Results saved to " + FILE_NAME + ". Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 5.");
            }
            System.out.println();
        }
        saveResults();
    }
}