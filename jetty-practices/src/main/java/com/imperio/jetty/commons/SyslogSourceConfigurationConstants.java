package com.imperio.jetty.commons;

public class SyslogSourceConfigurationConstants {
    public static final String CONFIG_PORT = "port";

    /**
     * List of ports to listen to.
     */
    public static final String CONFIG_PORTS = "ports";

    public static final String CONFIG_HOST = "host";

    public static final String CONFIG_FORMAT_PREFIX = "format.";

    public static final String CONFIG_REGEX = "regex";

    public static final String CONFIG_SEARCH = "search";

    public static final String CONFIG_REPLACE = "replace";

    public static final String CONFIG_DATEFORMAT = "dateFormat";

    /**
     * Number of processors used to calculate number of threads to spawn.
     */
    public static final String CONFIG_NUMPROCESSORS = "numProcessors";

    /**
     * Maximum allowable size of events.
     */
    public static final String CONFIG_EVENTSIZE = "eventSize";

    public static final String CONFIG_BATCHSIZE = "batchSize";

    public static final String CONFIG_CHARSET = "charset.default";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String CONFIG_PORT_CHARSET_PREFIX = "charset.port.";

    public static final int DEFAULT_BATCHSIZE = 100;

    public static final String CONFIG_PORT_HEADER = "portHeader";

    @Deprecated
    public static final String DEFAULT_PORT_HEADER = "port";

    public static final String CONFIG_READBUF_SIZE = "readBufferBytes";
    public static final int DEFAULT_READBUF_SIZE = 1024;

    public static final String CONFIG_KEEP_FIELDS = "keepFields";
    public static final String DEFAULT_KEEP_FIELDS = "none";

    public static final String CONFIG_KEEP_FIELDS_PRIORITY = "priority";
    public static final String CONFIG_KEEP_FIELDS_VERSION = "version";
    public static final String CONFIG_KEEP_FIELDS_TIMESTAMP = "timestamp";
    public static final String CONFIG_KEEP_FIELDS_HOSTNAME = "hostname";

    public static final String CONFIG_CLIENT_IP_HEADER = "clientIPHeader";
    public static final String CONFIG_CLIENT_HOSTNAME_HEADER = "clientHostnameHeader";

    private SyslogSourceConfigurationConstants() {
        // Disable explicit creation of objects.
    }
}
