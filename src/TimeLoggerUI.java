import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by bschenk on 6/27/17.
 */
public class TimeLoggerUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int menuChoice;
        List<WorkMonth> workMonths = new ArrayList<>();
        while(true){
            System.out.println("\n\n0. Exit");
            System.out.println("1. List months");
            System.out.println("2. List days");
            System.out.println("3. List tasks for a specific day");
            System.out.println("4. Add new month");
            System.out.println("5. Add day to a specific month");
            System.out.println("6. Start a task for a day");
            System.out.println("7. Finish a specific task");
            System.out.println("8. Delete a task");
            System.out.println("9. Modify task");
            System.out.println("10. Statistics");
            menuChoice = scanner.nextInt();
            switch (menuChoice){
                case 0:
                    System.exit(0);
                case 1:
                    listMonths(workMonths);
                    break;
                case 2:
                    listDays(workMonths, scanner);
                    break;
                case 3:
                    int monthIndex = listDays(workMonths, scanner);
                    List<WorkDay> workDays = workMonths.get(monthIndex - 1).getDays();

                    getDay(workDays, scanner).getTasks().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Please give the new month (yyyy mm)");
                    int menu4NewYear  = scanner.nextInt();
                    int menu4NewMonth = scanner.nextInt();
                    while(menu4NewYear < 1900 || menu4NewYear > 4000 || menu4NewMonth < 1 || menu4NewMonth > 12){
                        System.out.println("Wrong value(s), please try again (yyyy mm)");
                        menu4NewYear  = scanner.nextInt();
                        menu4NewMonth = scanner.nextInt();
                    }
                    workMonths.add(new WorkMonth(menu4NewYear, menu4NewMonth));
                    break;
                case 5:
                    listMonths(workMonths);
                    System.out.println("Please give the index of the month you want to have another day in");
                    int menu5MonthIndex = scanner.nextInt();
                    System.out.println("Please give the new day of the month");
                    int menu5NewDay = scanner.nextInt();
                    while(menu5NewDay < 1 || menu5NewDay > 31){
                        System.out.println("Wrong value, please try again");
                        menu5NewDay = scanner.nextInt();
                    }
                    System.out.println("Please give the required working hours (press enter for default 7.5)");
                    scanner.nextLine();
                    String line = scanner.nextLine();
                    double menu5RequiredWorkingHours;
                    if(!line.isEmpty()){
                        menu5RequiredWorkingHours = Double.parseDouble(line);
                    } else {
                        menu5RequiredWorkingHours = 7.5d;
                    }
                    WorkMonth workMonth = workMonths.get(menu5MonthIndex - 1);
                    workMonth.addWorkDay(new WorkDay(Math.round(menu5RequiredWorkingHours * 60),
                            LocalDate.of(workMonth.getDate().getYear(), workMonth.getDate().getMonth(), menu5NewDay)));

                    break;
                case 6:
                    int menu6MonthIndex = listDays(workMonths, scanner);
                    List<WorkDay> menu6workDays = workMonths.get(menu6MonthIndex - 1).getDays();
                    WorkDay menu6WorkDay = getDay(menu6workDays, scanner);
                    System.out.println("Please give the new task's details (id, comment, start time hh:mm)");
                    LocalTime localTime = null;
                    List<Task> menu6Tasks = menu6WorkDay.getTasks();
                    if(!menu6Tasks.isEmpty() && menu6Tasks.get(menu6Tasks.size() - 1).getEndTime() != null){
                        Task task = menu6Tasks.get(menu6Tasks.size() - 1);
                        localTime = task.getEndTime();
                        System.out.println("[" + task.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm")) + "]");
                    }
                    scanner.nextLine();
                    String menu6Id = scanner.nextLine();
                    String menu6Comment = scanner.nextLine();
                    line = scanner.nextLine();
                    if(!line.isEmpty()){
                        localTime = parseTime(line, scanner);
                    }
                    Task menu6NewTask = new Task(menu6Id);
                    menu6NewTask.setComment(menu6Comment);
                    menu6NewTask.setStartTime(localTime);
                    menu6Tasks.add(menu6NewTask);

                    break;
                case 7:
                    int menu7MonthIndex = listDays(workMonths, scanner);
                    List<WorkDay> menu7WorkDays = workMonths.get(menu7MonthIndex - 1).getDays();

                    List<Task> menu7Tasks = getDay(menu7WorkDays, scanner).getTasks().stream().filter(task -> task.getEndTime() == null).collect(Collectors.toList());
                    for(int i = 0; i < menu7Tasks.size(); i++){
                        System.out.println(i + 1 + ". " + menu7Tasks.get(i));
                    }
                    System.out.println("Please select a task");
                    int menu7TaskIndex = scanner.nextInt();
                    System.out.println("Please give end time for the task (hh:mm)");
                    line = scanner.nextLine();
                    LocalTime menu7EndTime = parseTime(line, scanner);
                    menu7Tasks.get(menu7TaskIndex - 1).setEndTime(menu7EndTime);



                    break;
                case 8:
                    int menu8MonthIndex = listDays(workMonths, scanner);
                    List<WorkDay> menu8WorkDays = workMonths.get(menu8MonthIndex - 1).getDays();

                    List<Task> menu8Tasks = getDay(menu8WorkDays, scanner).getTasks();
                    for(int i = 0; i < menu8Tasks.size(); i++){
                        System.out.println(i + 1 + ". " + menu8Tasks.get(i));
                    }
                    System.out.println("Please select a task");
                    int menu8TaskIndex = scanner.nextInt();
                    System.out.println("Are you sure you want to delete: " + menu8TaskIndex + ". " + menu8Tasks.get(menu8TaskIndex) + "(y/n)");
                    boolean menu8DeleteTask = scanner.nextLine().charAt(0) == 'y';
                    if(menu8DeleteTask){
                        menu8Tasks.remove(menu8TaskIndex);
                    }

                    break;
                case 9:
                    int menu9MonthIndex = listDays(workMonths, scanner);
                    List<WorkDay> menu9WorkDays = workMonths.get(menu9MonthIndex - 1).getDays();

                    List<Task> menu9Tasks = getDay(menu9WorkDays, scanner).getTasks();
                    for(int i = 0; i < menu9Tasks.size(); i++){
                        System.out.println(i + 1 + ". " + menu9Tasks.get(i));
                    }
                    System.out.println("Please select a task");
                    Task menu9Task = menu9Tasks.get(scanner.nextInt());
                    System.out.println("You can now modify the task");
                    System.out.print("Task ID [" + menu9Task.getTaskId() + "]: ");
                    line = scanner.nextLine();
                    String menu9NewTaskID = line.isEmpty() ? menu9Task.getTaskId() : line;
                    System.out.print("Start Time [" + menu9Task.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm")) + "] (hh:mm): ");
                    line = scanner.nextLine();
                    LocalTime menu9NewStartTime = line.isEmpty() ? menu9Task.getStartTime() : parseTime(line, scanner);
                    System.out.print("End Time [" + menu9Task.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm")) + "] (hh:mm): ");
                    line = scanner.nextLine();
                    LocalTime menu9NewEndTime = line.isEmpty() ? menu9Task.getEndTime() : parseTime(line, scanner);
                    System.out.print("Comment [" + menu9Task.getComment() + "]: ");
                    line = scanner.nextLine();
                    String menu9NewComment = line.isEmpty() ? menu9Task.getComment() : line;
                    menu9Task.setTaskId(menu9NewTaskID);
                    menu9Task.setStartTime(menu9NewStartTime);
                    menu9Task.setEndTime(menu9NewEndTime);
                    menu9Task.setComment(menu9NewComment);
                    break;
                case 10:
                    WorkMonth menu10Month = workMonths.get(selectMonth(workMonths, scanner) - 1);
                    System.out.println("Month's required min per month: " + menu10Month.getRequiredMinPerMonth() + ", sum: " + menu10Month.getSumPerMonth());
                    List<WorkDay> menu10WorkDays = menu10Month.getDays();
                    for(int i = 0; i < menu10WorkDays.size(); i++){
                        WorkDay workDay = menu10WorkDays.get(i);
                        System.out.println("\t" + i + 1 + ". " + workDay.getActualDay() + "required: " + workDay.getRequiredMinPerDay() + ", sum: " + workDay.getSumPerDay());
                    }
                    break;

            }
        }
    }
    public static void listMonths(List<WorkMonth> workMonths){
        int i = 1;
        for(WorkMonth workMonth : workMonths){
            System.out.println(i++ + ". " + workMonth.getDate().getYear() + "-" + workMonth.getDate().getMonth());
        }
    }

    public static int selectMonth(List<WorkMonth> workMonths, Scanner scanner){
        listMonths(workMonths);
        System.out.println("Please select one month by its index");
        int monthIndex = scanner.nextInt();
        while(monthIndex > workMonths.size()){
            System.out.println("\nWrong value, please try again");
            monthIndex = scanner.nextInt();
        }
        return monthIndex;
    }

    public static int listDays(List<WorkMonth> workMonths, Scanner scanner){
        int monthIndex = selectMonth(workMonths, scanner);
        List<WorkDay> workDays = workMonths.get(monthIndex - 1).getDays();
        for(int i = 0; i < workDays.size(); i++){
            System.out.println(i + 1 + ". " + workDays.get(i).getActualDay());
        }
        return monthIndex;
    }

    public static WorkDay getDay(List<WorkDay> workDays, Scanner scanner){
        System.out.println("Please select a day");
        int dayIndex = scanner.nextInt();
        while (dayIndex > workDays.size()){
            System.out.println("\nWrong value, please try again");
            dayIndex = scanner.nextInt();
        }
        return workDays.get(dayIndex - 1);
    }

    public static LocalTime parseTime(String line, Scanner scanner){
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d");

        while(!pattern.matcher(line).find()){
            System.out.println("Please give the time as follows hh:mm");
            line = scanner.nextLine();
        }
        String[] time = line.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        while(hour < 0 || hour > 23 || minute < 0 || minute > 59){
            time = scanner.nextLine().split(":");
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        }

        return LocalTime.of(hour, minute);

    }
}