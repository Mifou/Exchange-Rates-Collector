import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.IOException;

public class Scheduler {

    public static void main(String[] args) throws IOException, SchedulerException {

        JobDetail job = JobBuilder.newJob(CREResultsRetriever.class).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("getTrigger").withSchedule(SimpleScheduleBuilder
                .simpleSchedule().withIntervalInHours(24).repeatForever()).build();


        org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();

    }
}


