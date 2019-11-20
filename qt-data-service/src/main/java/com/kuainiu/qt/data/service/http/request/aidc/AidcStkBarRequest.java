package com.kuainiu.qt.data.service.http.request.aidc;

import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/29
 * Time: 7:06 PM
 */
@Data
public class AidcStkBarRequest extends BaseDataHttpRequest {
    private String instrument;

    private String frequency = "1m";

    private String fields = "limit_up,limit_down";

    private boolean skip_suspended = true;

    private String adjust_type = "none";
}
