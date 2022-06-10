package org.ratseno.demo.common.domain.test;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestPost {
    
    @ApiModelProperty(name = "title", example = "타이틀", dataType = "String")
    private String title;

    @ApiModelProperty(name = "body", example = "바디", dataType = "String")
    private String body;

    @ApiModelProperty(name = "userId", example = "1")
    private Long userId;
}
