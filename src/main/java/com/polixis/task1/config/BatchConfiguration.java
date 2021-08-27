package com.polixis.task1.config;

import com.polixis.task1.security.UnzipService;
import com.polixis.task1.service.PersonItemProcessor;
import com.polixis.task1.service.PersonService;
import com.polixis.task1.service.dto.PersonDTO;
import com.polixis.task1.service.job.ServiceItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

//    @Value("${fileInput.zipFile}")
//    private String zipPath;
//
//    @Value("${fileInput.directory}")
//    private String zipDestinationDir;

    @Value("${fileInput.dataFile}")
    private String inputResources;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    PersonService personService;

    @Autowired
    UnzipService unzipService;

//    @Value("${fileInput.directory}")
//    private String fileInput;

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public Job readCSVFilesJob() throws IOException {
        return jobBuilderFactory
            .get("readCSVFilesJob")
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .build();
    }

    @Bean
    public MultiResourceItemReader<PersonDTO> multiResourceItemReader()
    {
        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            resources = patternResolver.getResources(inputResources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultiResourceItemReader<PersonDTO> resourceItemReader = new MultiResourceItemReader<PersonDTO>();
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<PersonDTO> reader()
    {
        //Create reader instance
        FlatFileItemReader<PersonDTO> reader = new FlatFileItemReader<PersonDTO>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] {null ,"firstName", "lastName", "date"});
                    }
                });
                //Set values in PersonDTO class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonDTO>() {
                    {
                        setTargetType(PersonDTO.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<PersonDTO, PersonDTO>chunk(5)
            .reader(multiResourceItemReader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public ServiceItemWriter<PersonDTO> writer()
    {
        return new ServiceItemWriter<PersonDTO>();
    }

}
