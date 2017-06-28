package timelogger;

import timelogger.exception.EmptyTimeFieldException;
import timelogger.exception.FutureWorkException;
import timelogger.exception.NegativeMinutesOfWorkException;
import timelogger.exception.NotSeparatedTimesException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class WorkDay {
    private List<Task> tasks = new ArrayList<>();
    private long requiredMinPerDay;
    private LocalDate actualDay;
    private long sumPerDay;

    public WorkDay(long requiredMinPerDay, LocalDate actualDay) throws NegativeMinutesOfWorkException, FutureWorkException {
        if(requiredMinPerDay < 0){
            throw new NegativeMinutesOfWorkException();
        }
        if(actualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException();
        }
        this.requiredMinPerDay = requiredMinPerDay;
        this.actualDay = actualDay;
    }

    public WorkDay() throws NegativeMinutesOfWorkException, FutureWorkException {
        this(450, LocalDate.now());
    }

    public WorkDay(LocalDate actualDay) throws NegativeMinutesOfWorkException, FutureWorkException {
        this(450, actualDay);
    }

    public WorkDay(long requiredMinPerDay) throws NegativeMinutesOfWorkException, FutureWorkException {
        this(requiredMinPerDay, LocalDate.now());
    }

    public long getExtraMinPerDay() throws EmptyTimeFieldException {
        return getSumPerDay() - requiredMinPerDay;
    }


    public void addTask(Task t) throws NotSeparatedTimesException, EmptyTimeFieldException {
        if(!Util.isSeparatedTime(tasks, t)){
            throw new NotSeparatedTimesException();
        }


        if (Util.isMultipleQuarterHour(t.getStartTime(), t.getEndTime())) {
            tasks.add(t);
        }

    }

    public long getRequiredMinPerDay() {
        return requiredMinPerDay;
    }

    public LocalDate getActualDay() {
        return actualDay;
    }

    public long getSumPerDay() throws EmptyTimeFieldException {
        sumPerDay = 0;
        for(Task task : tasks){
            try {
                sumPerDay += task.getMinPerTask();
            } catch (EmptyTimeFieldException e) {
                throw e;
            }
        }
        return sumPerDay;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setRequiredMinPerDay(long requiredMinPerDay) throws NegativeMinutesOfWorkException {
        if(requiredMinPerDay < 0){
            throw new NegativeMinutesOfWorkException();
        }
        this.requiredMinPerDay = requiredMinPerDay;
    }

    public void setActualDay(int year, int month, int day) throws FutureWorkException {
        LocalDate tempActualDay = LocalDate.of(year, month, day);
        if(tempActualDay.isAfter(LocalDate.now())){
            throw new FutureWorkException();
        }
        this.actualDay = tempActualDay;
    }

    public LocalTime endTimeOfTheLastTask() throws EmptyTimeFieldException {
        if(tasks.size() == 0){
            return null;
        }
        return tasks.get(tasks.size() - 1).getEndTime();
    }
}
