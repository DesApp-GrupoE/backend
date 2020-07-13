package desapp.grupo.e.scheduler;

import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.service.purchase.PurchaseTurnService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseTurnJob implements Job {

    @Autowired
    private PurchaseTurnService purchaseTurnService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Log.info("Job send emails next to expire");
        purchaseTurnService.sendEmailsBeforeTurnPurchases();
    }
}
