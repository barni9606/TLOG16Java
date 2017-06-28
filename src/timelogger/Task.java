package timelogger;

import timelogger.exception.EmptyTimeFieldException;
import timelogger.exception.InvalidTaskIdException;
import timelogger.exception.NoTaskIdException;
import timelogger.exception.NotExpectedTimeOrderException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public Task(String taskId, int startHour, int startMin, int endHour, int endMin, String comment) throws NotExpectedTimeOrderException, EmptyTimeFieldException, InvalidTaskIdException, NoTaskIdException {
        this(taskId);
        LocalTime tempStartTime = LocalTime.of(startHour, startMin);
        LocalTime tempEndTime = Util.roundToMultipleQuarterHour(tempStartTime, LocalTime.of(endHour, endMin));
        if(tempStartTime.isAfter(tempEndTime)){
            throw new NotExpectedTimeOrderException();
        }
        this.startTime = tempStartTime;
        this.endTime = tempEndTime;
        this.comment = comment;
        if(this.comment == null){
            this.comment = "";
        }
    }

    public Task(String taskId, String startTime, String endTime, String comment) throws NotExpectedTimeOrderException, EmptyTimeFieldException, InvalidTaskIdException, NoTaskIdException {
        this(taskId);
        if(startTime == null || endTime == null){
           throw new EmptyTimeFieldException();
        }
        LocalTime tempStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("H:m"));
        LocalTime tempEndTime = Util.roundToMultipleQuarterHour(tempStartTime, LocalTime.parse(endTime, DateTimeFormatter.ofPattern("H:m")));
        if(tempStartTime.isAfter(tempEndTime)){
            throw new NotExpectedTimeOrderException();
        }
        this.startTime = tempStartTime;
        this.endTime = tempEndTime;
        this.comment = comment;
        if(this.comment == null){
            this.comment = "";
        }
    }

    public Task(String taskId) throws InvalidTaskIdException, NoTaskIdException {
        if(taskId == null || taskId.equals("")){
            throw new NoTaskIdException();
        }
        if(!(isValidLTTaskId(taskId) || isValidRedmineTaskId(taskId))){
            throw new InvalidTaskIdException();
        }
        this.taskId = taskId;
    }

    public long getMinPerTask() throws EmptyTimeFieldException {
        if(endTime == null || startTime == null){
            throw new EmptyTimeFieldException();
        }
        return (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
    }

    @Deprecated
    public boolean isValidTask(String taskId){
        Pattern pattern = Pattern.compile("^((\\d{4})|(LT-\\d{4})){1}$");
        return pattern.matcher(taskId).find();
    }

    public boolean isValidRedmineTaskId(String taskId){
        Pattern pattern = Pattern.compile("^\\d{4}$");
        return pattern.matcher(taskId).find();
    }

    public boolean isValidLTTaskId(String taskId){
        Pattern pattern = Pattern.compile("^LT-\\d{4}$");
        return pattern.matcher(taskId).find();
    }

    @Override
    public String toString() {
        return "timelogger.Task{" +
                "taskId='" + taskId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalTime getStartTime() throws EmptyTimeFieldException {
        if(startTime == null){
            throw new EmptyTimeFieldException();
        }
        return startTime;
    }

    public LocalTime getEndTime() throws EmptyTimeFieldException {
        if(startTime == null){
            throw new EmptyTimeFieldException();
        }
        return endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setTaskId(String taskId) throws NoTaskIdException, InvalidTaskIdException {
        if(taskId == null){
            throw new NoTaskIdException();
        }
        if(!(isValidLTTaskId(taskId) || isValidRedmineTaskId(taskId))){
            throw new InvalidTaskIdException();
        }
        this.taskId = taskId;
    }

    public void setStartTime(LocalTime startTime) throws NotExpectedTimeOrderException {
        this.startTime = startTime;
        if(endTime != null){
            if(this.startTime.isAfter(this.endTime)){
                throw new NotExpectedTimeOrderException();
            }
            endTime = Util.roundToMultipleQuarterHour(this.startTime, endTime);
        }
    }

    public void setStartTime(String startTime) throws NotExpectedTimeOrderException {
        setStartTime(LocalTime.parse(startTime, DateTimeFormatter.ofPattern("H:m")));
    }

    public void setStartTime(int startHour, int startMin) throws NotExpectedTimeOrderException {
        setStartTime(LocalTime.of(startHour, startMin));
    }

    public void setEndTime(LocalTime endTime) throws NotExpectedTimeOrderException {
        if(startTime == null){
            this.endTime = endTime;
            return;
        }
        this.endTime = Util.roundToMultipleQuarterHour(this.startTime, endTime);
        if(this.startTime.isAfter(this.endTime)){
            throw new NotExpectedTimeOrderException();
        }
    }

    public void setEndTime(String endTime) throws NotExpectedTimeOrderException {
        setEndTime(LocalTime.parse(endTime, DateTimeFormatter.ofPattern("H:m")));
    }

    public void setEndTime(int endHour, int endMin) throws NotExpectedTimeOrderException {
        setEndTime(LocalTime.of(endHour, endMin));

    }

    public void setComment(String comment) {
        this.comment = comment;
        if(this.comment == null){
            this.comment = "";
        }
    }
}
