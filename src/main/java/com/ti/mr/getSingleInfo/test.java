package com.ti.mr.getSingleInfo;

import com.ti.mr.getSingleInfo.utils.HbaseConnect;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class test extends  Thread{
    private final static HbaseConnect hbaseConnect=new HbaseConnect();

    @Override
    public void run() {
        super.run();
        System.out.println("run-----------------------------------------");
        System.out.println(HbaseConnect.conn);
        Table table=null;
        try {
             table=HbaseConnect.conn.getTable( TableName.valueOf("platform:sample"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(table);
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++)
        {
            new test().start();
        }

    }
}
