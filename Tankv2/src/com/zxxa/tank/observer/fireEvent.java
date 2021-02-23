package com.zxxa.tank.observer;

import com.zxxa.tank.Tank;

public class fireEvent extends Event<Tank> {
	
	Tank source;
	
	public fireEvent(Tank source) {
		this.source = source;
	}

	Tank getResource() {
		return source;
	}

}
