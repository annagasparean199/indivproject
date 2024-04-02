package org.example.listener;
import org.example.model.FinancialIndustryData;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.Collections;

@Component
public class CustomSkipListener implements SkipListener<FinancialIndustryData, FinancialIndustryData> {

    private final FlatFileItemWriter<FinancialIndustryData> failedRecordsWriter;

    public CustomSkipListener(FlatFileItemWriter<FinancialIndustryData> failedRecordsWriter) {
        this.failedRecordsWriter = failedRecordsWriter;
    }

    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println("Skipping invalid record during Read");
    }

    @Override
    public void onSkipInWrite(FinancialIndustryData item, Throwable throwable) {
        System.out.println("Skipping invalid record during Write: " + item);
    }

    @Override
    public void onSkipInProcess(FinancialIndustryData item, Throwable throwable) {
        try {
            failedRecordsWriter.open(new ExecutionContext());
            failedRecordsWriter.write(new Chunk<>(Collections.singletonList(item)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}