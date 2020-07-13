package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.scheduler.PurchaseTurnJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzBean {

    @Bean
    public JobDetail jobPurchaseTurn() {
        return JobBuilder.newJob(PurchaseTurnJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobPurchaseTurn) {
        return TriggerBuilder.newTrigger().forJob(jobPurchaseTurn)
                .withIdentity("Job Purchase Turn")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */15 * ? * *")) //Cada 15 minutos
                .build();
    }
}
