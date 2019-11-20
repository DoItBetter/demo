package com.kuainiu.qt.data.service.http.request.aidc;

import com.kuainiu.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/20
 * Time: 10:26 AM
 */
@Data
public class DCStkSnapshotRequest extends BaseDataHttpRequest {
    private List<String> instruments;

}
