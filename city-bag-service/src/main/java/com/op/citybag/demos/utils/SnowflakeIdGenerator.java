package com.op.citybag.demos.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

public class SnowflakeIdGenerator {

    // 起始的时间戳
    private final static long START_STAMP = 1609459200000L; // 2021-01-01 00:00:00

    // 每一部分占用的位数
    private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final static long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;// 数据中心占用的位数

    // 每一部分的最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心
    private long machineId;     // 机器标识
    private long sequence = 0L; // 序列号
    private long lastStamp = -1L;// 上一次时间戳

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    // 生成下一个ID
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT       // 数据中心部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                             // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }

    // 根据机器的IP生成数据中心ID和机器ID
    public static SnowflakeIdGenerator createInstance() {
        long datacenterId = 0L;
        long machineId = 0L;
        try {
            String ip = Inet4Address.getLocalHost().getHostAddress();
            int[] ipParts = new int[4];
            String[] ipSplit = ip.split("\\.");
            for (int i = 0; i < 4; i++) {
                ipParts[i] = Integer.parseInt(ipSplit[i]);
            }
            datacenterId = (ipParts[0] << 24) | (ipParts[1] << 16) | (ipParts[2] << 8) | ipParts[3];
            machineId = new Random().nextInt(32);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new SnowflakeIdGenerator(datacenterId, machineId);
    }
}
