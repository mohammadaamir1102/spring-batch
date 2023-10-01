package com.aamir.batch.config;

import com.aamir.batch.entity.SalesRecord;
import org.springframework.batch.item.ItemProcessor;

public class SaleRecordProcessor implements ItemProcessor<SalesRecord, SalesRecord> {
    @Override
    public SalesRecord process(SalesRecord salesRecord) throws Exception {

//        return salesRecord.getCountry().equals("Saudi Arabia") ? salesRecord : null;
//        above logic when we want to save/process records based on the condition then write logic here
        return salesRecord;
    }
}
