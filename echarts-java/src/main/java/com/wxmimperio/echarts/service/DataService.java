package com.wxmimperio.echarts.service;

import com.wxmimperio.echarts.entity.TaskInfo;
import com.wxmimperio.echarts.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataService {
    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);

    private JdbcTemplate prodJdbc;
    private JdbcTemplate testJdbc;

    @Autowired
    public DataService(JdbcTemplate jdbcTemplate, @Qualifier("secondJdbcTemplate") JdbcTemplate secondJdbcTemplate) {
        this.prodJdbc = jdbcTemplate;
        this.testJdbc = secondJdbcTemplate;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void cronPrepareData() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        prepareData(dateFormat.format(new Date()));
    }

    public void prepareData(String date) throws ParseException {
        long startTime = System.currentTimeMillis();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Map<Integer, String> jobMap = CommonUtils.getJobMap();
        String firstTimeStart = dateTimeFormat.format(dateFormat.parse(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String firstTimeEnd = dateTimeFormat.format(calendar.getTime());

        String taskSql = "SELECT realtime_job_id,first_begin_time,begin_time,end_time from realtime_task_list \n" +
                "where first_begin_time >= '" + firstTimeStart + "' and first_begin_time < '" + firstTimeEnd + "'\n" +
                "order by realtime_job_id,first_begin_time";

        LOG.info(taskSql);

        //prod
        List<Map<String, Object>> prodResult = prodJdbc.queryForList(taskSql);
        List<TaskInfo> prodTaskInfo = new ArrayList<>();
        prodResult.forEach(prod -> {
            // realtime_job_id
            // first_begin_time
            // begin_time
            // end_time
            int id = (Integer) prod.get("realtime_job_id");
            Timestamp beginTime = (Timestamp) prod.get("first_begin_time");
            Timestamp start = (Timestamp) prod.get("begin_time");
            Timestamp end = (Timestamp) prod.get("end_time");
            if (null != start && null != end) {
                TaskInfo taskInfo = new TaskInfo("prod", id, jobMap.get(id), dateFormat.format(beginTime), timeFormat.format(beginTime), (end.getTime() - start.getTime()));
                prodTaskInfo.add(taskInfo);
            }
        });

        // test
        List<Map<String, Object>> testResult = testJdbc.queryForList(taskSql);
        List<TaskInfo> testTaskInfo = new ArrayList<>();
        testResult.forEach(test -> {
            int id = (Integer) test.get("realtime_job_id");
            Timestamp beginTime = (Timestamp) test.get("first_begin_time");
            Timestamp start = (Timestamp) test.get("begin_time");
            Timestamp end = (Timestamp) test.get("end_time");
            if (null != start && null != end) {
                TaskInfo taskInfo = new TaskInfo("test", id, jobMap.get(id), dateFormat.format(beginTime), timeFormat.format(beginTime), (end.getTime() - start.getTime()));
                testTaskInfo.add(taskInfo);
            }
        });

        List<TaskInfo> allTask = new ArrayList<>();
        allTask.addAll(prodTaskInfo);
        allTask.addAll(testTaskInfo);

        String deleteSql = "DELETE FROM wxm_task_result WHERE `date` = '" + date + "'";
        LOG.info(deleteSql);
        int rows = testJdbc.update(deleteSql);
        LOG.info("delete rows size = " + rows);

        String insertSql = "INSERT INTO wxm_task_result(date,time,type,task_name,task_id,task_diff) values(?,?,?,?,?,?)";
        testJdbc.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, allTask.get(i).getDate());
                ps.setString(2, allTask.get(i).getTime());
                ps.setString(3, allTask.get(i).getType());
                ps.setString(4, allTask.get(i).getTaskName());
                ps.setInt(5, allTask.get(i).getId());
                ps.setLong(6, allTask.get(i).getDiff());
            }

            @Override
            public int getBatchSize() {
                return allTask.size();
            }
        });
        LOG.info("Flush " + date + " data , szie = " + allTask.size() + ", cost = " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public Map<Integer, List<TaskInfo>> getShowData(String type, String dateTime) {
        String sql = "";
        switch (type) {
            case "newLogin":
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id in " + CommonUtils.getNewLogin() + " order by task_id,time";
                break;
            case "login":
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id in " + CommonUtils.getLogin() + " order by task_id,time";
                break;
            case "remain":
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id in " + CommonUtils.getRemain() + " order by task_id,time";
                break;
            case "olnum":
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id in " + CommonUtils.getOlnum() + " order by task_id,time";
                break;
            case "deposit":
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id in " + CommonUtils.getDeposit() + " order by task_id,time";
                break;
            default:
                sql = "SELECT * from wxm_task_result where date = '" + dateTime + "' and task_id " + CommonUtils.getOther() + " order by task_id,time";
        }
        Map<Integer, List<TaskInfo>> map = new HashMap<>();
        List<Map<String, Object>> taskInfos = testJdbc.queryForList(sql);
        taskInfos.forEach(task -> {
            Integer id = (Integer) task.get("task_id");
            TaskInfo taskInfo = new TaskInfo(
                    (String) task.get("type"),
                    id,
                    (String) task.get("task_name"),
                    (String) task.get("date"),
                    (String) task.get("time"),
                    (Long) task.get("task_diff")
            );
            if (map.containsKey(id)) {
                map.get(id).add(taskInfo);
            } else {
                List<TaskInfo> list = new ArrayList<>();
                list.add(taskInfo);
                map.put(id, list);
            }
        });
        return map;
    }
}
