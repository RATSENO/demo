package org.ratseno.demo.common.domain.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "tb_test_post")
@Data
public class TestPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ApiModelProperty(name = "title", example = "타이틀", dataType = "String")
    @NotEmpty
    private String title;

    @ApiModelProperty(name = "body", example = "바디", dataType = "String")
    @NotEmpty
    private String body;

    @ApiModelProperty(name = "userId", example = "1")
    @NotEmpty
    private Long userId;
}
