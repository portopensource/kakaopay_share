package com.codingtest.portfolio.support;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseObject {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
