package com.fengwk.support.core.idgen;

/**
 * 
 * @author fengwk
 */
public class SnowIdGenerator implements IdGenerator<Long> {

    private final static long BIT_TIMESTAMP = 41L;// 时间戳占用位数
    private final static long BIT_DATACENTER = 5L;// 数据中心占用位数
    private final static long BIT_MACHINE = 5L;// 机器标识占用位数
    private final static long BIT_SEQUENCE = 12L;// 序列号占用位数
    
    private final static long MAX_TIMESTAMP = (1L << BIT_TIMESTAMP) - 1L;// 序列号最大值
    private final static long MAX_DATACENTER_ID = (1L << BIT_DATACENTER) - 1L;// 数据中心id最大值
    private final static long MAX_MACHINE_ID = (1L << BIT_MACHINE) - 1L;// 机器标识id最大值
    private final static long MAX_SEQUENCE = (1L << BIT_SEQUENCE) - 1L;// 序列号最大值
    
    private final static long TO_LEFT_TIMESTAMP = BIT_DATACENTER + BIT_MACHINE + BIT_SEQUENCE;// 时间戳左移位数
    private final static long TO_LEFT_DATACENTER_ID = BIT_MACHINE + BIT_SEQUENCE;// 数据中心id左移位数
    private final static long TO_LEFT_MACHINE_ID = BIT_SEQUENCE;// 机器标识id左移位数
    
    private final long initialTimestamp;// 初始时间戳
    private final long datacenterId;// 数据中心编号
    private final long machineId;// 机器编号
    
    private long lastTimestamp = -1;// 上一次时间戳
    private long sequence = -1;// 序列号
    
    public SnowIdGenerator(long initialTimestamp, long datacenterId, long machineId) {
        if (initialTimestamp > System.currentTimeMillis()) {
            throw new IllegalArgumentException("Initial timestamp cannot be greater than current time millis.");
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter id cannot be greater than " + MAX_DATACENTER_ID + " or less than zero.");
        }
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine id cannot be greater than " + MAX_MACHINE_ID + " or less than zero.");
        }
        this.initialTimestamp = initialTimestamp;
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }
    
    @Override
    public synchronized Long next() {
        long cms = currentMs();
        if (cms > lastTimestamp) {
            lastTimestamp = cms;
            sequence = 0;
        } else if (++sequence > MAX_SEQUENCE) {
            while ((cms = currentMs()) <= lastTimestamp);// 自旋直到更大的毫秒来临
            lastTimestamp = cms;
            sequence = 0;
        }
        return compound(lastTimestamp, sequence);
    }
    
    public long compound(long lt, long ns) {
        long offset = lt - initialTimestamp;
        if (offset > MAX_TIMESTAMP) {
            throw new IllegalStateException("Snow id is invalid because current timestamp greater than " + MAX_TIMESTAMP + ".");
        }
        return offset << TO_LEFT_TIMESTAMP | datacenterId << TO_LEFT_DATACENTER_ID | machineId << TO_LEFT_MACHINE_ID | ns;
    }
    
    private long currentMs() {
        return System.currentTimeMillis();
    }

}
