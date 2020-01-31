/*
 * Project: empi-core
 * 
 * File Created at 2019年12月28日
 * 
 * Copyright 2012 Greenline.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Greenline Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Greenline.com.
 */
package com.fengwk.support.core.domain.event;

/**
 * @author fengwk
 * @version V1.0
 * @since 2019-12-28 16:09
 */
public interface MultipleDomainEventSubscriber {

    void consume(DomainEvent event);
    
}
