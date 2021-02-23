package com.ti.mr.getSingleInfo.getInfo;

import com.ti.mr.getSingleInfo.utils.RegexInfo;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class JsonMapper extends Mapper<Text , Text,Text, Text> {
    Text k=new Text();
    Text v=new Text();
    String filename;
    Integer count=0;
    RegexInfo regexInfo=new RegexInfo();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit split=(FileSplit) context.getInputSplit();
        filename=split.getPath().getName();
    }

    @Override
    protected void map(Text  key, Text value, Context context) throws IOException, InterruptedException {
        //1转为string并切分
        count++;
        System.out.println(count);
        String line=value.toString();
        String info=regexInfo.find(line);
        String md5=filename.substring(0,-5);
        k.set(md5);
        v.set(info);
        context.write(k,v);
    }
}
