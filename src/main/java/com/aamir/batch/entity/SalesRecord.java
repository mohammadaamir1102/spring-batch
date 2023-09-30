package com.aamir.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "SALES_RECORD")
public class SalesRecord {

    @Id
    @Column(name = "SALES_ID")
    private Long salesId;
    @Column(name = "REGION")
    private String region;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "ITEM_TYPE")
    private String itemType;
    @Column(name = "SALES_CHANNEL")
    private String salesChannel;
    @Column(name = "ORDER_PRIORITY")
    private char orderPriority;

}
