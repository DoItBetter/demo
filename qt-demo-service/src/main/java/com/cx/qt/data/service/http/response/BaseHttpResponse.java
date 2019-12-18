package com.cx.qt.data.service.http.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/6/21
 * Time: 2:56 PM
 */
@Data
public class BaseHttpResponse implements Serializable {
    private static final long serialVersionUID = -8796135407367296842L;

    private String code;

    private String msg;
}
