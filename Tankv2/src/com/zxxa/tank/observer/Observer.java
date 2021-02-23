package com.zxxa.tank.observer;

import java.io.Serializable;

public interface Observer extends Serializable {
	void actionOnFire(fireEvent event);
}
