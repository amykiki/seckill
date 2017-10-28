package org.seckill.dto;

/**
 * 暴露秒杀地址dto
 */
public class Exposer {
    /**
     * 是否开启秒杀
     */
    private boolean exposable;
    /**
     * 加密措施，用于校验秒杀url
     */
    private String md5;

    private Long seckillId;

    /**
     * 系统当前时间(毫秒)
     */
    private long now;

    /**
     * 秒杀开始时间
     */
    private long start;
    /**
     * 秒杀结束时间
     */
    private long end;

    public Exposer(boolean exposable, String md5, Long seckillId) {
        this.exposable = exposable;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposable, Long seckillId, long now, long start, long end) {
        this.exposable = exposable;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposable, Long seckillId) {
        this.exposable = exposable;
        this.seckillId = seckillId;
    }

    public boolean isExposable() {
        return exposable;
    }

    public void setExposable(boolean exposable) {
        this.exposable = exposable;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposable=" + exposable +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
