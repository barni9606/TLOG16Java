package timelogger;

import timelogger.exception.EmptyTimeFieldException;
import timelogger.exception.NotExpectedTimeOrderException;

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

    public static boolean isMultipleQuarterHour(LocalTime startTime, LocalTime endTime) throws EmptyTimeFieldException, NotExpectedTimeOrderException {
        if(startTime == null || endTime == null){
            throw new EmptyTimeFieldException();
        }
        if(startTime.isAfter(endTime)){
            throw new NotExpectedTimeOrderException();
        }
        int min = (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
        return min % 15 == 0;
    }

    public static boolean isWeekDay(LocalDate day){
        DayOfWeek dayOfWeek = day.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public static boolean isSeparatedTime(List<Task> tasks, Task t){
        if(tasks.isEmpty()){
            return true;
        }

        return tasks.stream().allMatch(task -> {
            try {
                return     (task.getStartTime().isAfter(t.getEndTime()) ^ task.getEndTime().isBefore(t.getStartTime())
                        ^ task.getStartTime().equals( t.getEndTime()) ^ task.getEndTime().equals(  t.getStartTime()))
                        && !task.getStartTime().equals(t.getStartTime())
                        ;
            } catch (EmptyTimeFieldException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
