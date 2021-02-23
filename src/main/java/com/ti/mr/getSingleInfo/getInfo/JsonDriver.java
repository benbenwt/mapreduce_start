package com.ti.mr.getSingleInfo.getInfo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class JsonDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("hadoop.home.dir", "C:\\Users\\asus\\Desktop\\hadoop-3.1.4\\hadoop-3.1.4");
        //获取job对象
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);

        job.setInputFormatClass(WholeFileInputFormat.class);
        //设置jar位置
        job.setJarByClass(JsonDriver.class);
        //关联map和reduce
        job.setMapperClass(JsonMapper.class);
        job.setReducerClass(JsonReducer.class);
        //设置mapper阶段输出数据key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("C:\\Users\\asus\\Desktop\\input"));
        FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\asus\\Desktop\\output"));
        //提交job
        boolean result=job.waitForCompletion(true);
        System.exit(result?0:1);
    }
}
