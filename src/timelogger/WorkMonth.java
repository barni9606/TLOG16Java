package timelogger;

import timelogger.exception.EmptyTimeFieldException;
import timelogger.exception.NotNewDateException;
import timelogger.exception.NotTheSameMonthException;
import timelogger.exception.WeekendNotEnabledException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class WorkMonth {
    private List<WorkDay> days = new ArrayList<>();
    private YearMonth date;
    private long sumPerMonth;
    private long requiredMinPerMonth;

    public WorkMonth(int year, int month) {
        date = YearMonth.of(year, month);
    }

    public long getExtraMinPerMonth() throws EmptyTimeFieldException {
        long result = 0;
        for (WorkDay workDay : days) {
            result += workDay.getExtraMinPerDay();
        }
        return result;
    }

    public boolean isNewDate(WorkDay wd) {
        return days.stream().noneMatch(day -> day.getActualDay().equals(wd.getActualDay()));
    }

    public boolean isSameMonth(WorkDay wd) {
        return date.getYear() == wd.getActualDay().getYear() && date.getMonth().equals(wd.getActualDay().getMonth());
    }

    public void addWorkDay(WorkDay wd, boolean isWeekendEnabled) throws WeekendNotEnabledException, NotNewDateException, NotTheSameMonthException {

        if (isWeekendEnabled || Util.isWeekDay(wd.getActualDay())) {
            if (!isSameMonth(wd)) {
                throw new NotTheSameMonthException();
            } else if (!isNewDate(wd)) {
                throw new NotNewDateException();
            } else {
                days.add(wd);
            }
        } else {
            throw new WeekendNotEnabledException();
        }

    }

    public void addWorkDay(WorkDay wd) throws WeekendNotEnabledException, NotTheSameMonthException, NotNewDateException {
        addWorkDay(wd, false);
    }

    public List<WorkDay> getDays() {
        return days;
    }

    public YearMonth getDate() {
        return date;
    }

    public long getSumPerMonth() throws EmptyTimeFieldException {
        sumPerMonth = 0;
        for (WorkDay workDay : days) {
            sumPerMonth += workDay.getSumPerDay();
        }
        return sumPerMonth;
    }

    public long getRequiredMinPerMonth() {
        requiredMinPerMonth = 0;
        for (WorkDay workDay : days) {
            requiredMinPerMonth += workDay.getRequiredMinPerDay();
        }
        return requiredMinPerMonth;
    }
}
