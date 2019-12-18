package com.cx.qt.data.service.http.request.aidc;

import com.cx.qt.data.service.http.request.BaseDataHttpRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/9/20
 * Time: 10:26 AM
 */
@Data
public class DCStkSnapshotRequest extends BaseDataHttpRequest {
    private List<String> instruments;

}
