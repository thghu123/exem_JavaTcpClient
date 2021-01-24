import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import static org.quartz.SimpleScheduleBuilder.*;

public class TcpScheduler {


    public static void main(String[] args) throws IOException {
        try{
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();

            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail job = newJob(TcpClientJob.class)
                    .withIdentity("jobName", Scheduler.DEFAULT_GROUP)
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("trggerName", Scheduler.DEFAULT_GROUP)
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}


