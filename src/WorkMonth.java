import java.time.YearMonth;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class WorkMonth {
    private List<WorkDay> days;
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

    public WorkMonth(int year, int month) {
        date = YearMonth.of(year, month);
    }

    public long getExtraMinPerMonth(){
        long result = 0;
        for(WorkDay workDay : days){
            result += workDay.getExtraMinPerDay();
        }
        return result;
    }

    public boolean isNewDate(WorkDay wd){
        return days.stream().noneMatch(day -> day.getActualDay().equals(wd.getActualDay()));
    }

    public boolean isSameMonth(WorkDay wd){
        return date.getYear() == wd.getActualDay().getYear() && date.getMonth().equals(wd.getActualDay().getMonth());
    }

    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled){
        if(isSameMonth(wd) && isNewDate(wd) && (isWeekendEnabled || Util.isWeekDay(wd.getActualDay()))) {
            days.add(wd);
        }
    }

    public void addWorkDay(WorkDay wd){
        addWorkDay(wd, false);
    }
    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() {
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        return requiredMinPerMonth;
    }
}
