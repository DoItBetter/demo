package com.kuainiu.qt.data.service.http.bean;

import lombok.Data;

import java.util.Date;

@Data
public class InstrumentHttpBean extends BaseHttpBean {
    private String instrument;
    private String symbol;
    private String type;
    private Date start;
    private Date end;
}
