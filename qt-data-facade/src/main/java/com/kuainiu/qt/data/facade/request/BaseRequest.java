package com.kuainiu.qt.data.facade.request;



import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 7570981519307302023L;

    private String sysId;
}
