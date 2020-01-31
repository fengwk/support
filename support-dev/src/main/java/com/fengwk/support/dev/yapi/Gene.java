package com.fengwk.support.dev.yapi;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Gene extends Base {

	Gene(String type) {
		super.type = type;
	}
	
}
