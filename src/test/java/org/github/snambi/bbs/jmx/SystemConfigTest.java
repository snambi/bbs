package org.github.snambi.bbs.jmx;

import org.junit.Test;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by snambi on 7/15/17.
 */
public class SystemConfigTest {

    @Test
    public void jmxTest(){

        //Get the MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        //register the MBean
        SystemConfig mBean = new SystemConfig("test-config", 33);
        ObjectName name = null;

        try {

            name = new ObjectName("com.journaldev.jmx:type=SystemConfig");
            mbs.registerMBean(mBean, name);

        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

        do{
            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread Count="+mBean.getThreadCount()+":::Schema Name="+mBean.getName());

        }while(mBean.getThreadCount() !=0);

    }
}
