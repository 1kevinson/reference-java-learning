package example.billingjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;

public class BillingJob implements Job {

    @Override
    public String getName() {
        return "Billing Job";
    }

    @Override
    public void execute(JobExecution execution) {
        System.out.println("processing billing information");
    }
}
