package com.ti.mr.getSingleInfo.getInfo;

import com.ti.mr.getSingleInfo.utils.BeelineConnect;
import com.ti.mr.getSingleInfo.utils.HbaseConnect;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.sql.SQLException;

public class JsonReducer extends Reducer<Text, Text,Text, Text>{
    Text k=new Text();
    Text v=new Text();
    HbaseConnect hbaseConnect;
    BeelineConnect beelineConnect;
//    一个json是一个mapper
//    每个key创建一个connector
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

    }
}
