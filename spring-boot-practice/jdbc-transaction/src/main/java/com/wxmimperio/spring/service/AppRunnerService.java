package com.wxmimperio.spring.service;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.bean.TableOne;
import com.wxmimperio.spring.bean.TableThree;
import com.wxmimperio.spring.bean.TableTwo;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AppRunnerService implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments applicationArguments) throws Exception {
        JdbcService jdbcService = applicationContext.getBean(JdbcService.class);


        List<TableOne> tableOnes = Lists.newArrayList(
                new TableOne("wxm")
        );
        List<TableTwo> tableTwos = Lists.newArrayList(
                new TableTwo("15")
        );
        List<TableThree> tableThree = Lists.newArrayList(
                new TableThree("15")
        );
        jdbcService.saveTableOne(tableOnes);
        jdbcService.saveTableTwo(tableTwos);
        jdbcService.saveTableThree(tableThree);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
