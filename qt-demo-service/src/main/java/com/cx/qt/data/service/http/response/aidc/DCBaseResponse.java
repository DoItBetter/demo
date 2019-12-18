package com.cx.qt.data.service.http.response.aidc;

import com.cx.qt.data.service.http.response.BaseDataHttpResponse;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/8/6
 * Time: 4:24 PM
 */
@Data
public class DCBaseResponse<T> extends BaseDataHttpResponse {
    private T data;
}
