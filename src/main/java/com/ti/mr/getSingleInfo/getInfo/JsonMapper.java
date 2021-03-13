package com.ti.mr.getSingleInfo.getInfo;

import com.ti.mr.getSingleInfo.utils.BeelineConnect;
import com.ti.mr.getSingleInfo.utils.FilterAttribute;
import com.ti.mr.getSingleInfo.utils.HbaseConnect;
import com.ti.mr.getSingleInfo.utils.RegexInfo;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class JsonMapper extends Mapper<Text , Text,Text, Text> {
    Text k=new Text();
    Text v=new Text();
    String filePath;
    RegexInfo regexInfo=new RegexInfo();
//    HbaseConnect hbaseConnect=new HbaseConnect();
    BeelineConnect beelineConnect=new BeelineConnect();
    FilterAttribute filterAttribute;

    /*一个文件转为jsonObject，取数据库所需字段，单个列字符字段存入hive，hbase中。*/
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit split=(FileSplit) context.getInputSplit();
        filePath=split.getPath().toString();
        filterAttribute=new FilterAttribute();
    }

    @Override
    protected void map(Text  key, Text value, Context context) throws IOException, InterruptedException {
        //1转为string并切分
        String line=value.toString();
        Map<String,String> info=filterAttribute.getAttributeString(line);
        info.put("hdfs",filePath);


        String k_str="";
        k_str=info.get("md5");
        if(k_str==""||k_str==null)
        {
            k_str=info.get("cveid");
        }

        String value_str="";
        for(Map.Entry<String,String> entry:info.entrySet())
        {
            value_str=value_str+"  /t  "+entry.getValue();
        }

        k.set(k_str);
        v.set(value_str);
        context.write(k,v);

        //hbase和hive
//        HbaseConnect.insertSample(info);
        try {
            BeelineConnect.insertSample(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
//        hbaseConnect.close();
//        beelineConnect.close();
    }
}
