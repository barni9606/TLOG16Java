package timelogger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class Util {
    public static LocalTime roundToMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        int min = (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
        return endTime.minusMinutes(min).plusMinutes(Math.round(min / 15.0f) * 15);
    }

    public static boolean isMultipleQuarterHour(LocalTime startTime, LocalTime endTime){
        int min = (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
        return min % 15 == 0;
    }

    public static boolean isWeekDay(LocalDate day){
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public static boolean isSeparatedTime(List<Task> tasks, Task t){
        return tasks.stream().noneMatch(task -> task.getEndTime().isAfter(t.getEndTime()) && task.getStartTime().isBefore(t.getEndTime())
                    || task.getEndTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getStartTime()));
    }
}
