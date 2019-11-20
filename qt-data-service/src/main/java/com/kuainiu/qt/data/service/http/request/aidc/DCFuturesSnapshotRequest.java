package com.kuainiu.qt.data.service.http.request.aidc;

import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/25
 * Time: 7:40 PM
 */

@Data
public class DCFuturesSnapshotRequest extends BaseDataHttpRequest {

    private List<String> instruments;
}
