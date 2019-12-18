package com.cx.qt.data.service.http.response.aidc;

import com.cx.qt.data.service.http.bean.InstrumentHttpBean;
import com.cx.qt.data.service.http.response.BaseDataHttpResponse;
import lombok.Data;

@Data
public class InstrumentResponse extends BaseDataHttpResponse {
    private InstrumentHttpBean data;
}
