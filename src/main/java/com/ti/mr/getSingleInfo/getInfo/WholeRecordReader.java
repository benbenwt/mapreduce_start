package com.ti.mr.getSingleInfo.getInfo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import sun.misc.IOUtils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class WholeRecordReader extends RecordReader<Text, Text> {
    FileSplit split;
    Configuration configuration;
    Text k;
    Text v;
    boolean isProgress;
    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        this.split=(FileSplit)inputSplit;
        System.out.println("name:"+this.split.getPath().getName());
        this.configuration=taskAttemptContext.getConfiguration();
        k=new Text();
        v=new Text();
        isProgress=true;
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(isProgress)
        {
            Path path=split.getPath();
            String path_str=path.toString();
            path_str = path_str.substring(6);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path_str)));
            StringBuffer json=new StringBuffer();
            String tem="";
            while(true){
                tem = bufferedReader.readLine();
                if (tem != null) {
                    json.append(tem);
                } else break;
            }
            v.set(json.toString());
            k.set(path.toString());
            isProgress=false;
            return true;
        }
        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {

        return k;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
