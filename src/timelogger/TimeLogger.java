package timelogger;

import java.util.List;

/**
 * Created by bschenk on 6/27/17.
 */
public class TimeLogger {
    private List<WorkMonth> months;

    public boolean isNewMonth(WorkMonth wm){
        return months.stream().noneMatch(month -> month.getDate().equals(wm.getDate()));
    }

    public void addMonth(WorkMonth wm){
        if(isNewMonth(wm)){
            months.add(wm);
        }
    }

    public List<WorkMonth> getMonths() {
        return months;
    }
}
