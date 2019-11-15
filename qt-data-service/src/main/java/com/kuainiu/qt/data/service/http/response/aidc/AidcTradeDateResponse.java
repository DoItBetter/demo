package com.kuainiu.qt.data.service.http.response.aidc;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/10/22
 * Time: 9:54 PM
 */
@Data
public class AidcTradeDateResponse extends DCBaseResponse {
    private List<String> data = new ArrayList<>();
}
