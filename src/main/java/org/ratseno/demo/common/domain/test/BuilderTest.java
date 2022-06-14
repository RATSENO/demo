package org.ratseno.demo.common.domain.test;

import lombok.Builder;

public class BuilderTest {
    private String name;
    private String adderss;
    private String phoneNumber;
    private String sex;
    @Builder
    public BuilderTest(String name, String adderss, String phoneNumber, String sex){
        this.name = name;
        this.adderss = adderss;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }
}
