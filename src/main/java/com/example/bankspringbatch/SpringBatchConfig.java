package com.example.bankspringbatch;


import com.example.bankspringbatch.dao.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing // pour activer springBatch
public class SpringBatchConfig {
    // pour creer un job
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private ItemReader<BankTransaction /* input */> bankTransactionItemReader;
    @Autowired private ItemWriter<BankTransaction /* output */> bankTransactionItemWriter;
    @Autowired private ItemProcessor<BankTransaction/* input */, BankTransaction /* output mais les données vont changer*/> bankTransactionItemProcessor;


    // une methode de config qui permet de retourner un JOB
    @Bean
    public Job myJob() {
        //creer le step
        Step step=stepBuilderFactory.get("step-load-data")
                .<BankTransaction,BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();

        // retourner le job
        return jobBuilderFactory.get("bank-data-loader-job").start(step).build();
    }


    // creer mon itemReader ( pour les fichier plats)
    @Bean
    public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inputFile) {
        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("CSV-READER");
        flatFileItemReader.setLinesToSkip(1); // sauter la premiere ligne , la ligne d'entete
        flatFileItemReader.setResource(inputFile); // indiquer la ressource
        flatFileItemReader.setLineMapper(lineMapper()); // line Mapper un objet qui charge de trainer une ligne
        return flatFileItemReader;
    }

    // on creer le ligne mapper appele dans la methode ci-dessus
    @Bean
    public  LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<BankTransaction>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(","); // precisier c quoi le sepertaeur dans notre fichier
        lineTokenizer.setStrict(false);
        // mtn faut lui indiquer que chaque mot va etre stocher dans quelle attribut de notre model
        lineTokenizer.setNames("id", "accountID","strTransactionDate", "transactionType" ,"amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }





}
