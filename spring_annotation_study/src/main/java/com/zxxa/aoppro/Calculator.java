package com.zxxa.aoppro;

import org.springframework.stereotype.Service;

@Service
public interface Calculator {
    public int add(Integer i,Integer j);

    public int sub(Integer i,Integer j);

    public int mult(Integer i,Integer j);

    public int div(Integer i,Integer j);
}
