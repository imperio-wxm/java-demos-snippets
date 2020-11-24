package com.wxmimperio.log4j;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RateLimitAppender.java
 * @description This is the description of RateLimitAppender.java
 * @createTime 2020-11-16 18:50:00
 */
public class RateLimitAppenderOld extends FileAppender {

    private static final double DEFAULT_PERMIT_RATE = 1000.0D;

    private RateLimiter logRateLimiter;

    private double permitRate = DEFAULT_PERMIT_RATE;

    private boolean isRestricted = false;

    public RateLimitAppenderOld() {
        super();
    }

    public void setRate(int permitRate) {
        this.permitRate = permitRate;
    }

    @Override
    public void activateOptions() {
        super.activateOptions();

        logRateLimiter = RateLimiter.create(permitRate);
    }

    @Override
    protected void subAppend(LoggingEvent event) {
        if (!logRateLimiter.tryAcquire(1, TimeUnit.MICROSECONDS)) {
            if (isRestricted) {
                //如果被限流且之前已经被限流，直接return
                System.out.println("被限速！！" + event.getMessage());
            } else {
                isRestricted = true;
                this.qw.write("Saber Log Rate Limited");
                this.qw.write(Layout.LINE_SEP);
                if (shouldFlush(event)) {
                    this.qw.flush();
                }
            }
        } else {
            isRestricted = false;
            this.qw.write(this.layout.format(event));
            if (layout.ignoresThrowable()) {
                String[] s = event.getThrowableStrRep();
                if (s != null) {
                    int len = s.length;
                    for (int i = 0; i < len; i++) {
                        this.qw.write(s[i]);
                        this.qw.write(Layout.LINE_SEP);
                    }
                }
            }

            if (shouldFlush(event)) {
                this.qw.flush();
            }
        }
    }
}
