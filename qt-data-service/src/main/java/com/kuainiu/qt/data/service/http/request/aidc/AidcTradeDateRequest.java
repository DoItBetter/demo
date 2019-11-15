package com.kuainiu.qt.data.service.http.request.aidc;

import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 9:53 PM
 */
@Data
public class AidcTradeDateRequest extends BaseDataHttpRequest{
    private String begin_date;

    private String end_date;

    private Integer type;
}
