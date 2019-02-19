package com.wxmimperio.spring;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;


import java.io.Serializable;

public class GenerateUsingIdentity extends IdentityGenerator {

    @Override
    public Serializable generate(SessionImplementor s, Object obj) {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        System.out.println("=======" + id);
        return id != null ? id : super.generate(s, obj);
    }
}
