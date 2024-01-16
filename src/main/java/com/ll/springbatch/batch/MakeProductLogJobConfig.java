package com.ll.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class MakeProductLogJobConfig {
    @Bean
    public Job makeProductLogJob(JobRepository jobRepository, Step makeProductLogStep1) {
        return new JobBuilder("makeProductLogJob", jobRepository)
                .start(makeProductLogStep1)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @JobScope
    @Bean
    public Step makeProductLogStep1(
            JobRepository jobRepository,
            Hello4Step1Reader makeProductLogStep1Reader,
            Hello4Step1Processor makeProductLogStep1Processor,
            Hello4Step1Writer makeProductLogStep1Writer,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("makeProductLogStep1Tasklet", jobRepository)
                .<Integer, String>chunk(10, platformTransactionManager)
                .reader(makeProductLogStep1Reader)
                .processor(makeProductLogStep1Processor)
                .writer(makeProductLogStep1Writer)
                .build();
    }

    // 원본 데이터 읽기
    @StepScope
    @Component
    public static class Hello4Step1Reader implements ItemReader<Integer> {
        @Override
        public Integer read() {
            int no = (int) (Math.random() * 500);

            if (no == 100) return null;

            return no;
        }
    }

    // 원본 데이터 가공해서 파생 데이터 생성
    // EX : 50 -> "no. 50"
    @StepScope
    @Component
    public static class Hello4Step1Processor implements ItemProcessor<Integer, String> {
        @Override
        public String process(Integer item) {
            return "no. " + item;
        }
    }

    // 파생 데이터를 화면에 출력
    @StepScope
    @Component
    public static class Hello4Step1Writer implements ItemWriter<String> {

        @Override
        public void write(Chunk<? extends String> chunk) {
            List<String> items = (List<String>) chunk.getItems();
            for (String item : items) {
                System.out.println("item = " + item);
            }
        }
    }
}