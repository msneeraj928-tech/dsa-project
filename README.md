# DSA PROJECT
# Result Analysis System

A console-based Java application that analyses student results: it calculates total, percentage and grade, finds the topper and class average, and prints a rank list sorted by total marks. Results are saved to a file.

**Course:** Data Structures and Algorithms (DSA), RU-200-01-00029 | **Problem Statement:** PS No. 5
**Submitted by:** M Sai Neeraj (ERP ID: RU-25-10760)
**Guide:** Mr. Abhijeet Dutta, SME
**University:** School of Computer Science and Engineering, Rungta International Skills University, Bhilai, Chhattisgarh

## Features

- Add a student result (name + marks of 3 subjects)
- Automatic total, percentage and grade calculation
- Search a result by student name (case-insensitive, HashMap lookup)
- Rank list sorted by total marks (highest first)
- Topper and class average
- Input validation: marks must be between 0 and 100; blank names, duplicate names and non-numeric input are rejected
- Results are loaded from `results.txt` at start and saved on exit

## Concepts Used

`ArrayList` · `HashMap` · Sorting (`Comparator`) · Searching · Methods · File Handling (`BufferedReader` / `BufferedWriter`)

## Grading Scheme

| Percentage | Grade |
|------------|-------|
| 90 and above | A+ |
| 75 – 89 | A |
| 60 – 74 | B |
| 40 – 59 | C |
| Below 40 | F |

## Requirements

- JDK 8 or above
- Windows or Linux, with Command Prompt / Terminal (or VS Code / IntelliJ IDEA)

## How to Run

Open a terminal in the project folder and run:

```bash
javac Main.java
java Main
```

> Java is case-sensitive: the file must be named `Main.java` and run with `java Main`.
> `results.txt` is created in the folder you run the program from.

## Menu

```
====== Result Analysis System ======
1. Add Result
2. Search Result by Name
3. Rank List
4. Topper & Class Average
5. Exit
```

## Sample Run

```
Enter your choice: 1
Enter student name: Riya
Enter marks of 3 subjects (comma separated): 80, 90, 70
Result added. Riya | Total: 240 | 80.0% | Grade A

Enter your choice: 1
Enter student name: Aman
Enter marks of 3 subjects (comma separated): 60, 55, 65
Result added. Aman | Total: 180 | 60.0% | Grade B

Enter your choice: 3
--- RANK LIST ---
1. Riya | 240 | 80.0%
2. Aman | 180 | 60.0%

Enter your choice: 2
Enter student name: Aman
Aman | Total: 180 | 60.0% | Grade B

Enter your choice: 4
Topper: Riya (240)
Class average: 70.0%
```

## Test Cases

| # | Scenario | Input | Output | Status |
|---|----------|-------|--------|--------|
| 1 | Normal input | Option 1, Riya, 80, 90, 70 | `Result added. Riya \| Total: 240 \| 80.0% \| Grade A` | Pass |
| 2 | Boundary / minimum input | Only Riya (240), option 4 | `Topper: Riya (240)` / `Class average: 80.0%` | Pass |
| 3 | Invalid input | Option 1, Dev, 105, 60, 70 | `Invalid marks. Enter marks between 0 and 100.` | Pass |
| 4 | Different valid scenario | Add Aman 60, 55, 65; rank list; search Aman | Rank list Riya, Aman; `Aman \| Total: 180 \| 60.0% \| Grade B` | Pass |
| 5 | Minimum marks boundary | Option 1, Zero, 0, 0, 0 | `Result added. Zero \| Total: 0 \| 0.0% \| Grade F` | Pass |

## File Format (`results.txt`)

One student per line as `name|mark1|mark2|mark3`:

```
Riya|80|90|70
Aman|60|55|65
```

Percentage and grade are recalculated when the file is loaded.

## Time Complexity

Let N be the number of students.

| Operation | Complexity |
|-----------|------------|
| Add result | O(1) amortized |
| Search by name | O(1) average |
| Rank list (sorting) | O(N log N) |
| Topper and class average | O(N) |
| Load / Save | O(N) |

## Project Structure

```
Result Analysis System/
├── Main.java        # Complete source code (Main class + nested Student class)
├── results.txt      # Saved results (created at runtime)
└── README.md
```

## Future Scope

Edit/delete results, configurable number of subjects, database (MySQL) integration, GUI (Swing/JavaFX), roll numbers, pass/fail analysis, report export (PDF/CSV), user authentication.
