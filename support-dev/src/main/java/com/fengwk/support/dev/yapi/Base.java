package com.fengwk.support.dev.yapi;

import lombok.Data;

/**
 * @author fengwk
 */
@Data
public abstract class Base {

	protected String type;
	
	protected String color = "#5e677d";
	
	protected String description;// 备注
	
	protected String introduction;// 说明
	
}
