package com.ti.mr.getSingleInfo.getInfo;

import com.ti.mr.getSingleInfo.utils.HbaseConnect;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class JsonReducer extends Reducer<Text, Text,Text, Text>{
    Text k=new Text();
    Text v=new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //合并相同key
        System.out.println("reduce");
        HbaseConnect hbaseConnect=new HbaseConnect();
        hbaseConnect.init();
        int sum=0;
        for(Text value:values)
        {
            hbaseConnect.insert(key.toString(),value.toString());
            k.set(key);
            v.set(value);
            context.write(k,v);
        }

    }
}
