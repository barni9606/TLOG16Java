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
}
