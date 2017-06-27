import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bschenk on 6/27/17.
 */
public class Task {
    private String taskId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comment;

    public Task(String taskId, int startHour, int startMin, int endHour, int endMin, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.of(startHour, startMin);
        this.endTime = LocalTime.of(endHour, endMin);
        this.comment = comment;
    }

    public Task(String taskId, String startTime, String endTime, String comment) {
        this.taskId = taskId;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.comment = comment;
    }

    public long getMinPerTask(){
        return (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
    }

    public boolean isValidTask(){
        Pattern pattern = Pattern.compile("^((\\d{4})|(LT-\\d{4})){1}$");
        return pattern.matcher(taskId).find();
    }

    public boolean isMultipleQuarterHour(){
        return getMinPerTask() % 15 == 0;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }
}
