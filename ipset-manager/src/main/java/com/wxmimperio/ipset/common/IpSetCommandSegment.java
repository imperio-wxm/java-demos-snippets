package com.wxmimperio.ipset.common;

public class IpSetCommandSegment {

    public static final String CREATE_IPSET_WITH_MAXZISE = "sudo ipset create %s hash:ip maxelem %s";

    public static final String CREATE_IPSET = "sudo ipset create %s hash:ip";

    public static final String CREATE_IPSET_WITH_PROTOCAL_MAXZISE = "sudo ipset create %s hash:ip,port maxelem %s";

    public static final String CREATE_IPSET_WITH_PROTOCAL = "sudo ipset create %s hash:ip,port";

    public static final String ADD_IP_TO_IPSET = "ipset add %s %s -exist";

    public static final String ADD_IP_TO_IPSET_WITH_PROTOCAL = "ipset add %s %s,%s:%s -exist";

    public static final String LIST_IPSET = "ipset list %s";

    public static final String DELETE_IP_FROM_IPSET = "ipset del %s %s -exist";

    public static final String DELETE_IP_FROM_IPSET_WITH_PROTOCAL = "ipset del %s %s,%s:%s -exist";

    public static final String TEST_IPSET = "ipset test %s %s";

    public static final String DESTROY_IPSET = "ipset destroy %s";

    public static final String SAVE_IPSET = "ipset save %s -f %s";

    public static final String RESTORE_IPSET = "ipset restore -f %s";

}
