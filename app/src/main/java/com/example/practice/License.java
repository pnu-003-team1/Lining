package com.example.practice;

/**
 * Created by Soo on 2019-04-30.
 */

public class License {
    private boolean full;
    private String bname;
    private String addr;
    private String tel;

    public License(boolean full, String bname, String addr, String tel){
        this.full = full;
        this.bname = bname;
        this.addr = addr;
        this.tel = tel;
    }

    public boolean isFull(){
        return full;
    }

    public void setFull(boolean full){
        this.full = full;
    }

    public String getBname(){
        return bname;
    }

    public void setBsname(String bname){
        this.bname = bname;
    }

    public String getAddr(){
        return addr;
    }

    public void setAddr(String addr){
        this.addr = addr;
    }

    public String getTel(){
        return tel;
    }

    public void setTel(String tel){
        this.tel = tel;
    }
}
