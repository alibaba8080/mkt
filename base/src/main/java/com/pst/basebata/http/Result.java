package com.pst.basebata.http;


public class Result<T> {
    public static final int SUCCESSED = 0;

    public int code;
    public String message;
    public T data;
    public int total;  //条目总数
}
