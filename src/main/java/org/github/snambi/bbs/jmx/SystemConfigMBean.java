package org.github.snambi.bbs.jmx;

/**
 */
public interface  SystemConfigMBean {

    public void setThreadCount(int noOfThreads);
    public int getThreadCount();

    public void setName(String name);
    public String getName();
}
