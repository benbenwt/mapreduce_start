package com.ti.mr.getSingleInfo.utils;


import java.sql.*;

public class BeelineConnect {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public void  insert() throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
        Connection con = DriverManager.getConnection("jdbc:hive2://hbase:10000/default", "root", "root");
        String sql="show tables user_test";
        Statement st=con.createStatement();
        ResultSet rt=st.executeQuery(sql);
        while(rt.next())
        {
            System.out.println(rt.getString(1));
        }
    }

    public static void main(String[] args) throws SQLException {
        BeelineConnect beelineConnect=new BeelineConnect();
        beelineConnect.insert();
    }
}
