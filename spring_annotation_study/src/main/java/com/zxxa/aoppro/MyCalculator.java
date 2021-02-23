package com.zxxa.aoppro;

import org.springframework.stereotype.Service;

@Service
public class MyCalculator implements Calculator {

    public int add(Integer i, Integer j) {
        int result = i + j;
        return result;
    }

    public int sub(Integer i, Integer j) {
        int result = i - j;
        return result;
    }

    public int mult(Integer i, Integer j) {
        int result = i * j;
        return result;
    }

    public int div(Integer i, Integer j) {
        int result = i / j;
        return result;
    }

}
