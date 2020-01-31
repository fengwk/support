package com.fengwk.support.dev.yapi;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Obj extends Base {

	protected String title;
	
	protected Map<String, Base> properties;
	
	protected List<String> required;
	
	Obj(String title, Map<String, Base> properties, List<String> required) {
		this.title = title;
		this.properties = properties;
		this.required = required;
	}
	
}
