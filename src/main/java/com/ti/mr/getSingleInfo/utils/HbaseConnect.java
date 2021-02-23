package com.ti.mr.getSingleInfo.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseConnect {
    Connection conn;
    Admin admin;
    public void init() throws IOException {
        System.out.println("hbase init--------------------------------------------");
        conn=null;
        System.setProperty("hadoop.home.dir", "C:\\Users\\asus\\Desktop\\hadoop-3.1.4\\hadoop-3.1.4");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","hbase");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
            System.out.println("连接hbase成功");
        } catch (IOException e){
            System.out.println("连接hbase失败");
            e.printStackTrace();
        }
        admin=conn.getAdmin();
    }

//    private void createTable(String name)
//    {
//        Admin admin=conn.getAdmin();
//        TableName tableName=TableName.valueOf(name);
//        TableDescriptor tableDescriptor=new TableDescriptor(tableName);
//        admin.createTable(TableDescriptor);
//    }
    public void testScan(String name) throws IOException {
        TableName tableName=TableName.valueOf(name);
        Table table=conn.getTable(tableName);
        if(admin.tableExists(tableName))
        {
            System.out.println("exist");
        }
        Scan scan = new Scan();
        ResultScanner resultScanner = table.getScanner(scan);
        for(Result result: resultScanner){
            System.out.println("scan:  " + result);
        }
    }
    public void retrieveByRowKey(String name,String rowKey)
    {
    }
    public void deleteByRowKey(String name,String rowKey)
    {
        TableName tableName=TableName.valueOf(name);
        Table table=null;
        try {
             table=conn.getTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Delete delete=new Delete(Bytes.toBytes(rowKey));
        try {
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void testDelete(String name){
        TableName tableName=TableName.valueOf(name);
        try {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void insert(String row,String value)
    {
        System.out.println("Info:"+value);
        String [] splits=value.split("\t");
        String last_seen=splits[0];
        String malware_types=splits[1];
        String family=splits[2];
        TableName tableName=TableName.valueOf("test");
        try {
            Table table=conn.getTable(tableName);
            Put put=new Put(Bytes.toBytes(row));
            put.addColumn("id".getBytes(),"last_seen".getBytes(),last_seen.getBytes());
            put.addColumn("id".getBytes(),"malware_types".getBytes(),malware_types.getBytes());
            put.addColumn("id".getBytes(),"family".getBytes(),family.getBytes());
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void countCategory()
    {

    }

    public static void main(String[] args) {
        HbaseConnect hbaseConnect=new HbaseConnect();
        try {
            hbaseConnect.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hbaseConnect.insert("123","123");
    }
}
