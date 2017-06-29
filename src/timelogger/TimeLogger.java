package timelogger;

import timelogger.exception.NotNewMonthException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class TimeLogger {
    private List<WorkMonth> months = new ArrayList<>();

    public boolean isNewMonth(WorkMonth wm){
        return months.stream().noneMatch(month -> month.getDate().equals(wm.getDate()));
    }

    public void addMonth(WorkMonth wm) throws NotNewMonthException {
        if(isNewMonth(wm)){
            months.add(wm);
        } else {
            throw new NotNewMonthException();
        }
    }

    public List<WorkMonth> getMonths() {
        return months;
    }
}
