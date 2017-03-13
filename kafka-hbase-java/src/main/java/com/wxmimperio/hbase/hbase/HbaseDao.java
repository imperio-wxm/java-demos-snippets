package com.wxmimperio.hbase.hbase;

import com.wxmimperio.hbase.common.ParamsConst;
import com.wxmimperio.hbase.common.PropManager;
import com.wxmimperio.hbase.pojo.Bslog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/2/23.
 */
public class HbaseDao {

    public static Configuration configuration;

    final static byte COLUMN_SEPARATOR = 0x01;

    private static String tableName = PropManager.getInstance().getPropertyByString(ParamsConst.HBASE_TABLENAME);

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", PropManager.getInstance().getPropertyByString(ParamsConst.HBASE_ZOOKEEPER_PORT));
        configuration.set("hbase.zookeeper.quorum", PropManager.getInstance().getPropertyByString(ParamsConst.HBASE_ZOOKEEPER));
        configuration.set("hbase.master", PropManager.getInstance().getPropertyByString(ParamsConst.HBASE_MASTER));
    }

    public static boolean putList(List<Bslog> bslogs) {
        boolean success = false;
        try {
            HBaseAdmin admin = new HBaseAdmin(configuration);
            HTable table = new HTable(configuration, tableName.valueOf(tableName));
            List<Put> putList = new ArrayList<>();
            if (!admin.tableExists(Bytes.toBytes(tableName))) {
                System.err.println("the " + tableName + " is not exist");
                System.exit(1);
            }
            String rowKeys;
            String value;
            for (Bslog bslog : bslogs) {
                rowKeys = bslog.getPtId() + COLUMN_SEPARATOR + bslog.getOrderId();
                value = bslog.getSettleTime() + COLUMN_SEPARATOR + bslog.getAmount();

                Put put = new Put(Bytes.toBytes(rowKeys));
                put.add(Bytes.toBytes("cf"), Bytes.toBytes("v"), Bytes.toBytes(value));
                putList.add(put);
            }
            table.put(putList);
            table.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
