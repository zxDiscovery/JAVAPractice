package com.zxxa.aopdemo;

import java.sql.SQLException;

import com.zxxa.proxy.CalculatorProxy;

public class Mytest {

	public static void main(String[] args) throws SQLException {
		
		Calculator calculator = CalculatorProxy.getProxy(new MyCalculator());
		
		calculator.add(1, 2);
		calculator.div(2, 1);
		
		System.out.println(calculator.getClass());
	}
}
