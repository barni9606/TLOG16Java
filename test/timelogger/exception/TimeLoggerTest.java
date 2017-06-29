package timelogger.exception;

import org.junit.Assert;
import org.junit.Test;
import timelogger.Task;
import timelogger.TimeLogger;
import timelogger.WorkDay;
import timelogger.WorkMonth;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by bschenk on 6/29/17.
 */
public class TimeLoggerTest {

    @Test
    public void test01() throws Exception {
        WorkDay workDay = new WorkDay(LocalDate.of(2016, 4, 14));
        WorkMonth workMonth = new WorkMonth(2016, 4);
        Task task = new Task("0123", "7:30", "10:30", "");
        workDay.addTask(task);
        workMonth.addWorkDay(workDay);
        TimeLogger timeLogger = new TimeLogger();
        timeLogger.addMonth(workMonth);
        Assert.assertEquals(task.getMinPerTask(), timeLogger.getMonths().get(0).getSumPerMonth());
    }

    @Test(expected = NotNewMonthException.class)
    public void test02() throws Exception {
        WorkMonth workMonth1 = new WorkMonth(2016, 4);
        WorkMonth workMonth2 = new WorkMonth(2016, 4);
        TimeLogger timeLogger = new TimeLogger();
        timeLogger.addMonth(workMonth1);
        timeLogger.addMonth(workMonth2);
    }

}