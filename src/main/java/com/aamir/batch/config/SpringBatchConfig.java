package com.aamir.batch.config;

import com.aamir.batch.entity.SalesRecord;
import com.aamir.batch.repo.SalesRecordRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@AllArgsConstructor
@EnableBatchProcessing // enable batch processing.
public class SpringBatchConfig {

    /*
     I will inject two factory class for Job and Step
     1-
     2-
    */
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private SalesRecordRepo salesRecordRepo;

    @Bean
    public FlatFileItemReader<SalesRecord> reader() {
        FlatFileItemReader<SalesRecord> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("/src/main/resources/salesrecord.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);// skipping first header line of csv file.
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<SalesRecord> lineMapper() {
        DefaultLineMapper<SalesRecord> defaultLineMapper = new DefaultLineMapper<>();


        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(","); // for reading the comma separated fields
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("Sales Id", "Region", "Country", "Item Type", "Sales Channel", "Order Priority");

    }


}
