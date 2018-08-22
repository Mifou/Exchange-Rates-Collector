import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.IOException;

public class Scheduler {

    public static void main(String[] args) throws IOException, SchedulerException {

        JobDetail job = JobBuilder.newJob(CreateCRE.class).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("getTrigger").withSchedule(SimpleScheduleBuilder
                .simpleSchedule().withIntervalInHours(24).repeatForever()).build();


        org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}


