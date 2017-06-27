import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class WorkDay {
    private List<Task> tasks;
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;

    public WorkDay(long requiredMinPerDay, LocalDate actualDay) {
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = actualDay;
    }

    public WorkDay() {
        this(450, LocalDate.now());
    }

    public long getExtraMinPerDay(){
        return requiredMinPerDay - sumPerDay;
    }

    public boolean isSeparatedTime(Task t){
        for(Task task : tasks) {
            if (task.getEndTime().isAfter(t.getEndTime()) && task.getStartTime().isBefore(t.getEndTime())
                    || task.getEndTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    public void addTask(Task t){
        if(t.isMultipleQuarterHour() && isSeparatedTime(t)){
            tasks.add(t);
        }
    }

    public boolean isWeekDay(){
        DayOfWeek dayOfWeek = actualDay.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() {
        return sumPerDay;
    }
}
