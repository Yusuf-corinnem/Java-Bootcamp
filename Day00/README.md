# Day 00 – Java bootcamp
### Management structures and arrays

###  Rules of the Day
- User-defined methods and classes are prohibited for all tasks of the day, except for user-defined static functions and procedures in the main class file of the solution.
- All tasks include a list of ALLOWED language constructs for the specific task.
- System::exit can be used for all tasks.

- All tasks include an example of how the application works. The implemented solution must be identical to the specified output example for the current input data.
- For illustration purposes, user-entered data in task examples is preceded by an arrow (->). Do not consider these arrows when implementing a solution!

P.S. Some problems require a non-trivial approach due to the limitations mentioned above. These limitations will teach you how to find solutions for automating real business processes.

### Exercise 00 – Sum of Digits

|Exercise 00: Sum of Digits||
|------|------|
| Turn-in directory | ex00 |
| Files to turn-in |	Program.java |
| **Allowed** | |
| Input/Output | System.out|
| Types |	Primitive types |
| Operators |	Standard operations of primitive types|

Java is a strictly typed programming language. The basic data types (boolean, character, integer, float) are represented in Java by eight primitive types: boolean, char, byte, short, int, long, float, double.

Working with the integer type.

Calculating the sum of the digits of a six-digit int number (the value of the number is set directly in the code by explicitly initializing the number variable). 

Example of program operation for number 479598:
```
$ java Program
  42
```

### Exercise 01 – Really Prime Number

|Exercise 01: Really Prime Number||
|------|------|
| Turn-in directory |	ex01 |
| Files to turn-in |	Program.java |
| **Allowed** | |
|Input/Output	| System.out, System.err, Scanner(System.in) |
| Types |	Primitive types |
| Operators |	Standard operations of primitive types, conditions, loops |

According to the Böhm-Jacopini theorem, any algorithm can be written using three statements: sequence, selection, and iteration.

Using these statements in Java, you must determine whether the input number is a prime number. A prime is a number that has no divisors other than itself and 1.

The program will take the number typed on the keyboard as input and display the result of the test to see if this number is a prime.  In addition, the program outputs the number of steps (iterations) required to perform the check. In this task, an iteration is a single comparison operation. 

For negative numbers, 0 and 1, display the message IllegalArgument and exit the program with the code -1.

Example of program operation:

```
$ java Program
-> 169
   false 12

$ java Program
-> 113
   true 10

$ java Program
-> 42
   false 1

$ java Program
-> -100 
   Illegal Argument
```

### Exercise 02 – Endless Sequence (or not?)

|Exercise 02: Endless Sequence (or not?)||
|------|------|
Turn-in directory |	ex02
Files to turn-in |	Program.java
**Allowed**
Input/Output |	System.out, System.err, Scanner(System.in)
Types |	Primitive types
Operators |	Standard operations of primitive types, conditions, loops

Today you are Google. 

You have to count the queries related to coffee preparation that users of our search system make at any given moment. Obviously, the sequence of queries is infinite. It is impossible to store these queries and count them later. 

But there is a solution — process the flow of queries. Why should we waste our resources on all queries if we are only interested in one particular feature of this sequence of queries?  Let's assume that each query is a natural number other than 0 and 1. A query is related to making coffee only if the sum of the digits of the number (query) is a prime number.

So we need to implement a program that counts the number of elements for a given set of numbers whose sum of digits is a prime number.

To keep it simple, let's assume that this potentially infinite sequence of queries is still limited, and that the last element of the sequence is number 42.

This task guarantees that the input data is absolutely correct.

Example of how the program works:

```
$ java Program
-> 198131
-> 12901212
-> 11122
-> 42
   Count of coffee-request – 2
```

### Exercise 03 – A Little Bit of Statistics
Exercise 03: A Little Bit of Statistics ||
---|---
Turn-in directory |	ex03
Files to turn-in |	Program.java
**Allowed**
Input/Output | System.out, System.err, Scanner(System.in)
Types |	Primitive types, String
Operators	| Standard operations of primitive types, conditions, loops
Methods |	String::equals

When developing enterprise systems, you often need to collect various types of statistics. And the customer always wants such analysis to be illustrative. Who needs cold, dry numbers? 

Educational institutions and online schools are often this type of customer. Now you need to implement functionality to visualize student progress. The customer wants to see a graph that shows the change in student progress over several weeks. 

The customer evaluates this progress as a minimum grade for five tests within each week. Each test can be scored between 1 and 9.

The maximum number of weeks for analysis is 18. Once the program has obtained information for each week, it displays the graph on the console to show the minimum grades for a particular week.

And we still assume that 42 is the limit of input data. 

The exact guaranteed number of tests in a week is 5.

However, the order of the weekly data entry is not guaranteed, so the data of week 1 can be entered after the data of week 2. If the order of data entry is wrong, an IllegalArgument message is displayed and the program is terminated with a -1 code.

**Note**:

1.	There are many ways to store information, and arrays are just one of them. Use a different method for storing student test data without using arrays.
2.	Concatenating strings often results in unexpected program behavior. If there are many iterations of a concatenation operation in a cycle for a single variable, an application can slow down significantly. Therefore, we should not use string concatenation inside a loop to generate a result.

Example of program operation:

```
$ java Program
-> Week 1
-> 4 5 2 4 2
-> Week 2
-> 7 7 7 7 6
-> Week 3
-> 4 3 4 9 8
-> Week 4
-> 9 9 4 6 7
-> 42
Week 1 ==>
Week 2 ======>
Week 3 ===>
Week 4 ====>
```

### Exercise 04 – A Bit More of Statistics
Exercise 04: A Bit More of Statistics ||
---|---
Turn-in directory	| ex04
Files to turn-in |	Program.java
**Allowed**
Input/Output |	System.out, System.err, Scanner(System.in)
Types | Primitive types, String, arrays
Operators	| Standard operations of primitive types, conditions, loops
Methods	| String::equals, String::toCharArray, String::length

Did you know that you can use frequency analysis to decode poorly encrypted text?

 Check "Frequency_analysis" in [Wikipedia](https://en.wikipedia.org/wiki/Frequency_analysis).

Feel like a hacker and implement a program to count the occurrences of a character in a text. 

We like visual clarity. Therefore, the program will display the results in a histogram. This graph will show the 10 most common characters in descending order. 

If characters occur the same number of times, they should be sorted in a lexicographic order.

Each character can occur many times in a text. For this reason, the graph should be scalable. The maximum height of the displayed graph is 10, and the minimum is 0. 

The input data for the program is a string with a single "\n" character at the end (so a single long string can be used as input).

It is assumed that each input character can be contained in a char variable (Unicode BMP; for example, the code of the letter "S" is 0053, the maximum code value is 65535).

The maximum number of character occurrences is 999.

**Note**: this problem must be solved without multiple iterations over the source text (sorting and removing repetitions), because these methods will slow down the application significantly. Use other methods of information processing.

Example of program operation:

```
$ java Program

-> AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFKKDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWWWWWOOOOOOO42

 36
  #  35
  #   #
  #   #  27
  #   #   #
  #   #   #
  #   #   #
  #   #   #  14  12
  #   #   #   #   #   9
  #   #   #   #   #   #   7   4
  #   #   #   #   #   #   #   #   2   2
  D   A   S   W   L   K   O   T   E   R
```

### Exercise 05 – Schedule

Exercise 05: Schedule ||
---|---
Turn-in directory	| ex05
Files to turn-in |Program.java
**Allowed**
Input/Output | System.out, System.err, Scanner(System.in)
Types |	Primitive types, String, arrays
Operators	| Standard operations of primitive types, conditions, loops
Methods |	String::equals, String::toCharArray, String::length

You've just become a great hacker, but your client comes back to you with another task. This time, they need to be able to maintain a class schedule for their educational institution. The client opens a school in September 2020. So you need to implement the MVP version of the project for that month only. 

You need to be able to create a list of students and specify the time and weekdays for classes. Classes can be held any day of the week between 13:00 and 18:00. Multiple classes may be held on a single day. However, the total number of classes per week cannot exceed 10.

The maximum number of students in a class is also 10. Maximum length of student name is 10 (no spaces).

You should also provide a way to record student attendance. To do this you need to specify the time and date of the class next to each student's name and the attendance status (HERE, NOT_HERE). You do not need to record attendance for all classes in one month.

Therefore, the life cycle of the application is as follows:
1. Create a list of students.
2. Fill a timetable — each class (time, day of the week) is entered in a separate row.
3. Record attendance.
4. Display the timetable in tabular form with attendance status.

Each stage of application operation is separated by "." (period). Absolute correctness of data is guaranteed, except for the sequential order of classes when filling the timetable.

Example of how the program works:

![program](misc/images/program.png)
