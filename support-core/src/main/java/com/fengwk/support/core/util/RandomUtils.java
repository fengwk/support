package com.fengwk.support.core.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author fengwk
 */
public class RandomUtils {

    private RandomUtils() {}
    
    /**
     * 获取[startInclusive,endExclusive)区间的随机数
     * 
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static int nextInt(Integer startInclusive, Integer endExclusive) {
        return ThreadLocalRandom.current().nextInt(endExclusive - startInclusive) + startInclusive;
    }

}
