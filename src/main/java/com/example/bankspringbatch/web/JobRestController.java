package com.example.bankspringbatch.web;


import com.example.bankspringbatch.BankTransactionItemAnalyticsProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobRestController {

    @Autowired
    private JobLauncher jobLauncher; // pour lancer un job

    @Autowired
    private Job job;

    @Autowired
    private BankTransactionItemAnalyticsProcessor bankTransactionItemAnalyticsProcessor;

    @GetMapping("/startJob")
    public BatchStatus load() throws Exception {
        // parametrer l'execution
        Map<String, JobParameter> params = new HashMap<String, JobParameter>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters joParameters = new JobParameters(params);

        JobExecution jobExecution = jobLauncher.run(job, joParameters); // lancer le job

        while(jobExecution.isRunning()){
            System.out.println("............");
        }

        return jobExecution.getStatus();

    }


    @GetMapping("/analytics")
    public Map<String,Double> analytics(){
        Map<String,Double> map = new HashMap<>();
        map.put("totalCredit", bankTransactionItemAnalyticsProcessor.getTotalCredit());
        map.put("totalDebit", bankTransactionItemAnalyticsProcessor.getTotalDebit());
        return map;
    }
}
