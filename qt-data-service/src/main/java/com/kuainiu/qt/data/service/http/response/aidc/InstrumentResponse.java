package com.kuainiu.qt.data.service.http.response.aidc;

import com.kuainiu.qt.data.service.http.bean.InstrumentHttpBean;
import com.kuainiu.qt.data.service.http.response.BaseDataHttpResponse;
import lombok.Data;

@Data
public class InstrumentResponse extends BaseDataHttpResponse {
    private InstrumentHttpBean data;
}
