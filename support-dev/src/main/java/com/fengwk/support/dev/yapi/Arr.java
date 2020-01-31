package com.fengwk.support.dev.yapi;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Arr extends Base {

	protected Base items;
	
	Arr(Base items) {
		super.type = "array";
		this.items = items;
	}
	
}
