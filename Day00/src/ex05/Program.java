import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        start();
    }

    static void start() {
        String[] month = {"", "TU", "WE", "TH", "FR", "SA", "SU",
                "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                "MO", "TU", "WE", "TH", "FR", "SA", "SU",
                "MO", "TU", "WE"};
        String[] studentList = new String[10];
        int[][] schedule = new int[5][7];
        int[][][] visits = new int[10][6][31];
        String[][] tableSchedules = new String[11][10];

        input(studentList, schedule, visits);
        fillTable(studentList, schedule, visits, tableSchedules, month);
        drawTable(tableSchedules);
    }

    static void input(String[] studentList, int[][] schedule, int[][][] visits) {
        Scanner scanner = new Scanner(System.in);
        fillStudentList(studentList, scanner);
        fillSchedule(schedule, scanner);
        fillVisits(visits, scanner, studentList);
    }

    static void fillStudentList(String[] str, Scanner scanner) {
        String temp = "";

        for (int i = 0; i < str.length; ++i) {
            temp = scanner.nextLine();

            if (temp.length() > 10) {
                System.out.println("Incorrect length name. Max: 10 chars");
                System.exit(-1);
            }

            if (temp.equals(".")) break;
            str[i] = temp;
        }
    }

    static void fillSchedule(int[][] schedule, Scanner scanner) {
        String temp = "";
        int time = 0, week = 0;

        for (int i = 0; i < 10; ++i) {
            temp = scanner.nextLine();
            if (temp.equals(".")) break;
            String[] tempSplit = temp.split(" ");
            time = Integer.parseInt(tempSplit[0]);
            week = getIndexDayOfWeek(tempSplit[1]);

            if (week == -1) {
                System.out.println("Incorrect day of week");
                System.exit(-1);
            }
            schedule[time][week] = time;
        }
    }

    static void fillVisits(int[][][] visits, Scanner scanner, String[] studentList) {
        String temp = "";
        int time = 0, day = 0, indexName;

        for (int i = 0; i < visits.length; ++i) {
            temp = scanner.nextLine();
            if (temp.equals(".")) break;

            String[] tempSplit = temp.split(" ");
            time = Integer.parseInt(tempSplit[1]);
            day = Integer.parseInt(tempSplit[2]);
            indexName = getIndexStudent(tempSplit[0], studentList);

            if (indexName == -1) {
                System.out.println("Incorrect name");
                System.exit(-1);
            }

            if (tempSplit[3].equals("HERE")) visits[indexName][time][day] = 1;
            else if (tempSplit[3].equals("NOT_HERE")) visits[indexName][time][day] = -1;
            else {
                System.out.println("Incorrect");
                System.exit(-1);
            }
        }

    }

    static void fillTable(String[] studentList, int[][] schedule, int[][][] visits, String[][] table, String[] month) {
        String[] daysOfWeek = {"", "MO", "TU", "WE", "TH", "FR", "SA", "SU"};

        table[0][0] = "\t";
        for (int day = 1, k = 1; day < month.length; ++day) {
            for (int i = 0; i < schedule.length; ++i) {
                for (int j = 0; j < schedule[i].length; ++j) {
                    if (schedule[i][j] == 0 || !month[day].equals(daysOfWeek[j])) continue;

                    fillTableCap(day, schedule, daysOfWeek, table, k, i, j);
                    fillVisitsInTable(studentList, schedule, table, visits, day, k, i, j);

                    if (k < 9) k++;
                }
            }

        }

        fillStudentListInTable(studentList, table);
    }

    static void fillTableCap(int day, int[][] schedule, String[] daysOfWeek, String[][] table, int k, int i, int j) {
        if (day >= 10)
            table[0][k] = Integer.toString(schedule[i][j]) + ":00 " + daysOfWeek[j] + " " + Integer.toString(day) + "|";
        else
            table[0][k] = Integer.toString(schedule[i][j]) + ":00 " + daysOfWeek[j] + "  " + Integer.toString(day) + "|";
    }

    static void fillVisitsInTable(String[] studentList, int[][] schedule, String[][] table, int[][][] visits, int day, int k, int i, int j) {
        for (int s = 0; s < studentList.length; ++s) {
            if (studentList[s] == null) break;

            if (visits[s][schedule[i][j]][day] == 1) table[s + 1][k] = "         " + "1|";
            else if (visits[s][schedule[i][j]][day] == -1) table[s + 1][k] = "        " + "-1|";
            else table[s + 1][k] = "          |";
        }
    }

    static void fillStudentListInTable(String[] studentList, String[][] table) {
        for (int i = 0, j = 1; i < studentList.length; ++i, ++j) {
            table[j][0] = studentList[i];
        }
    }

    static void drawTable(String[][] table) {
        boolean flagNull = false;

        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[i].length; ++j) {
                if (table[i][j] == null) {
                    flagNull = true;
                    break;
                }
                System.out.print(table[i][j]);
            }

            if (flagNull) break;
            System.out.println();
        }
    }

    static int getIndexDayOfWeek(String str) {
        String[] daysOfWeek = {"", "MO", "TU", "WE", "TH", "FR", "SA", "SU"};

        for (int i = 1; i < daysOfWeek.length; ++i) {
            if (str.equals(daysOfWeek[i])) return i;
        }

        return -1;
    }

    static int getIndexStudent(String str, String[] studentList) {
        for (int i = 0; i < studentList.length; ++i) {
            if (str.equals(studentList[i])) return i;
        }

        return -1;
    }
}