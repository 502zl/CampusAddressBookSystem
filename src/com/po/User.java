package com.po;

/*
 * �����˻���Ϣ��
 */

public class User {
    private String uaccount;//�˺�
    private String upwd;//����
    private String uname;//����
    private String usex;    //�Ա�
    private String uphone;//�ֻ�
    private String urole;   //���
    public String getUaccount() {
        return uaccount;
    }
    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }
    public String getUpwd() {
        return upwd;
    }
    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUphone() {
        return uphone;
    }
    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }

    @Override
    public String toString() {
        return "User{" +
                "uaccount='" + uaccount + '\'' +
                ", upwd='" + upwd + '\'' +
                ", uname='" + uname + '\'' +
                ", usex='" + usex + '\'' +
                ", uphone='" + uphone + '\'' +
                ", urole='" + urole + '\'' +
                '}';
    }
}
