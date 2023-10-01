package com.aamir.batch.config;

import com.aamir.batch.entity.SalesRecord;
import com.aamir.batch.repo.SalesRecordRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing // enable batch processing.
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SalesRecordRepo salesRecordRepo;

    @Bean
    public FlatFileItemReader<SalesRecord> reader() {
        final String CSV_FILE_RESOURCE_LOCATION = "src/main/resources/salesrecord.csv";
        FlatFileItemReader<SalesRecord> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(CSV_FILE_RESOURCE_LOCATION));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);// skipping first header line of csv file.
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<SalesRecord> lineMapper() {
        final String DELIMITER = ",";
        DefaultLineMapper<SalesRecord> lineMapper = new DefaultLineMapper<>();

        // Extract the value from the CSV file
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(DELIMITER); // for reading the comma separated fields
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("Sales Id", "Region", "Country", "Item Type", "Sales Channel", "Order Priority");

        // here need to map particular information with object
        // It is responsible to map csv file to customer object
        BeanWrapperFieldSetMapper<SalesRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(SalesRecord.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }

    @Bean
    public SaleRecordProcessor processor() {
        return new SaleRecordProcessor();
    }

    @Bean
    public RepositoryItemWriter<SalesRecord> writer() {
        final String SAVE_METHOD = "save";
        RepositoryItemWriter<SalesRecord> writer = new RepositoryItemWriter<>();
        writer.setRepository(salesRecordRepo);
        writer.setMethodName(SAVE_METHOD);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("sales-record-csv-step")
                .<SalesRecord, SalesRecord>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importSalesRecordInformation")
                .flow(step1())
//              .next(step()2)  here we can provide multiples step
                .end().build();
    }

}
