package com.example.job.entity;

import org.springframework.util.StringUtils;

/**
 * @Auther: ymfa
 * @Date: 2020/2/16 14:22
 * @Description:
 */
public class WaterNode {
    String xld;
    String xxy;

    public String getXld() {

        return StringUtils.hasText(xld)? xld:"";
    }

    public void setXld(String xld) {
        this.xld = xld;
    }

    public String getXxy() {
        return StringUtils.hasText(xxy)?xxy:"";
    }

    public void setXxy(String xxy) {
        this.xxy = xxy;
    }
}
