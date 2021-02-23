package com.ti.mr.getSingleInfo.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    Text k=new Text();
    IntWritable v=new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1转为string并切分
        System.out.println(key);
        String line=value.toString();
        String[] words=line.split(" ");
        //循环写出
        for(String word:words)
        {
            k.set(word);
            context.write(k,v);
        }
    }
}
