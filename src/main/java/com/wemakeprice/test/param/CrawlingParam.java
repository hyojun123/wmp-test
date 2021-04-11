package com.wemakeprice.test.param;

import com.wemakeprice.test.param.enumParam.TypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrawlingParam {
    private String url;
    private Integer unitCnt;
    private TypeEnum type;
}
