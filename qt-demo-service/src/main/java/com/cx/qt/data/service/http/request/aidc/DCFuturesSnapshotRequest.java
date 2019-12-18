package com.cx.qt.data.service.http.request.aidc;

import com.cx.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/25
 * Time: 7:40 PM
 */

@Data
public class DCFuturesSnapshotRequest extends BaseDataHttpRequest {

    private List<String> instruments;
}
