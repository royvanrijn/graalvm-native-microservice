package oracle.as.jmx.framework;

import javax.management.MBeanServer;

/**
 * A shadow class, causes no mbeans to be loaded and/or registered.
 *
 * GraalVM doesn't support JMX.
 */
public class PortableMBeanFactory {

    public MBeanServer getMBeanServer() {
        // Do nothing.
        return null;
    }
}
