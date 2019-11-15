package com.kuainiu.qt.data.service.http.response.aidc;

import com.kuainiu.qt.data.service.http.response.BaseDataHttpResponse;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/8/6
 * Time: 4:24 PM
 */
@Data
public class DCBaseResponse<T> extends BaseDataHttpResponse {
    private T data;
}
