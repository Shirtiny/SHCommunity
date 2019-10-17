package cn.shirtiny.community.SHcommunity.Utils;

import java.util.Arrays;

//队列
public class ShArrayQueue {

    private long MaxSize;
    private long front;
    private long rear;
    private long[] array;
    private long size=0;

    //初始化数组
    public ShArrayQueue(long MaxSize){

        this.MaxSize=MaxSize;
        array=new long[(int)MaxSize];
        front=-1;
        rear=-1;

    }

    //判断队列是否满
     boolean isFull(){
        return rear==MaxSize-1;
    }

    //判断队列是否为空
    boolean isEmpty(){
        return rear==front;
    }

    //向队列增加数据
    public void add(long data){

        if (isFull()){//判断队列是否已满
            System.out.println("队列已满，无法存");
        }else {
            rear++;//尾指针后移
            array[(int)rear]=data;//赋值
            this.size=this.size+1;
        }

    }

    //从队列取数据
    public long get(){

       if (isEmpty()){//判断队列是否为空
           throw new RuntimeException("队列为空，无法取");
       }else {
           front++;
           return array[(int)front];//取值
       }

    }

    //显示整个队列
    public void show(){
        if (isEmpty()){//判断队列是否为空
            throw new RuntimeException("队列空，无数据");
        }else {
            for (long data:array){//打印数组
             System.out.print(data+"\t");
            }
            System.out.println();
        }

    }

    //展示队列头数据
    public long showFront(){
        if (isEmpty()){//判断队列是否为空
            throw new RuntimeException("队列空，无数据");
        }else {
           return array[(int)front+1];//返回头数据，指针不变
            }
        }


    //展示队列尾数据
    public long showRear(){
        if (isEmpty()){//判断队列是否为空
            throw new RuntimeException("队列空，无数据");
        }else {
            return array[(int)rear];//返回尾数据，指针不变
        }

    }

    //返回队列长度
    public long getSize() {
        return this.size;
    }

    //返回最大容量
    public long getMaxSize() {
        return this.MaxSize;
    }

    //返回数组
    public long[] toArray(){
        return Arrays.copyOfRange(this.array,(int)front+1,(int)rear+1);
    }
}