package com.zxxa.tank.observer;

import java.io.Serializable;

public abstract class Event<T> {
	abstract T getResource();
}
