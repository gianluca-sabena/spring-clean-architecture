package com.example.clean.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.example.clean.adapter.DbUserRepository;
//import com.example.clean.adapter.DbUserRepositoryJdbc;
import com.example.clean.usecase.FindUser;
import com.example.clean.usecase.port.UserRepository;
import com.example.clean.entities.user.User;

@Configuration
public class BatchConfiguration {

    @Autowired
    private DbUserRepository dbUserRepository;

    @Autowired
    public BatchConfiguration(final DbUserRepository dbUserRepositoryJdbc) {
        this.dbUserRepository = dbUserRepositoryJdbc;
    }

    //private final UserRepository userRepository = new DbUserRepository();
    //private final FindUser findUser = new FindUser(userRepository);
    @Autowired
	private UserReader userReader;
    // https://docs.spring.io/spring-batch/reference/readers-and-writers/flat-files/file-item-writer.html#SimplifiedFileWritingExample
    @Bean
    public FlatFileItemWriter<User> reader() {
        return new FlatFileItemWriterBuilder<User>()
                .name("userItemReader")
                .resource(new FileSystemResource("output.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    // @Bean
    // public ItemReaderAdapter<User> itemReader() {
    //     ItemReaderAdapter<User> reader = new ItemReaderAdapter<User>();
    //     reader.setTargetObject(findUser);
    //     reader.setTargetMethod("findAllUsers");
    //     return reader;
    // }


    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager, FlatFileItemWriter<User>  writer) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(3, transactionManager)
                .reader(userReader)
                .writer(writer)
                .build();
    }
    // end::jobstep[]
}
