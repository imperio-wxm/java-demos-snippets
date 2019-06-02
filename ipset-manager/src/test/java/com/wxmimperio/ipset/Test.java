package com.wxmimperio.ipset;

import com.google.common.collect.Lists;
import com.wxmimperio.ipset.entity.IpSet;
import com.wxmimperio.ipset.entity.Protocol;

import java.util.List;

public class Test {

    @org.junit.Test
    public void test() {
        List<IpSet.Member> list = Lists.newArrayList();
        List<IpSet.Member> list1 = Lists.newArrayList();
        IpSet.Member member1 = new IpSet.Member("127.0.0.1", Protocol.TCP, 5240);
        IpSet.Member member2 = new IpSet.Member("127.0.0.1", Protocol.UDP, 5240);
        IpSet.Member member3 = new IpSet.Member("127.0.0.1", Protocol.UDP, 5240);

        list.add(member1);
        list.add(member2);
        list1.add(member3);

        list.retainAll(list1);

        System.out.println(list);
    }
}
