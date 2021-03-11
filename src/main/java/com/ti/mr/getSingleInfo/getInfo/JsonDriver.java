package com.ti.mr.getSingleInfo.getInfo;


import com.ti.mr.getSingleInfo.utils.BeelineConnect;
import com.ti.mr.getSingleInfo.utils.HbaseConnect;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.SQLException;

public class JsonDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, IOException, SQLException {
        System.setProperty("hadoop.home.dir", "/root/module/hadoop-3.1.4");
        //获取job对象
        Configuration conf=new Configuration();
        conf.set("mapred.map.tasks","15");
        Job job=Job.getInstance(conf);

        job.setInputFormatClass(WholeFileInputFormat.class);
        //设置jar位置
        job.setJarByClass(JsonDriver.class);
        //关联map和reduce
        job.setMapperClass(JsonMapper.class);
        //设置mapper阶段输出数据key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //提交job
        boolean result=job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
