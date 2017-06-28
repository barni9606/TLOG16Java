import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class WorkDay {
    private List<Task> tasks = new ArrayList<>();
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


    public void addTask(Task t){
        if(Util.isMultipleQuarterHour(t.getStartTime(), t.getEndTime()) && Util.isSeparatedTime(tasks, t)){
            tasks.add(t);
        }
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) {
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(int year, int month, int day) {
        this.actualDay = LocalDate.of(year, month, day);
    }
}
