package com.po;

/*
 * ��ϵ��
 */
public class Liaison {
    private String laccount;//�˺�
    private int lno;//���
    private String lnumber; //���
    private String lname;//����
    private String lsex;//�Ա�
    private String lphone;//�ֻ�
    private String lrole;//��ɫ
    public String getLaccount() {
        return laccount;
    }
    public void setLaccount(String laccount) {
        this.laccount = laccount;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getLsex() {
        return lsex;
    }
    public void setLsex(String lsex) {
        this.lsex = lsex;
    }
    public String getLphone() {
        return lphone;
    }
    public void setLphone(String lphone) {
        this.lphone = lphone;
    }
    public int getLno() {
        return lno;
    }
    public void setLno(int lno) {
        this.lno = lno;
    }

    public String getLrole() {
        return lrole;
    }

    public void setLrole(String lrole) {
        this.lrole = lrole;
    }

    public String getLnumber() {
        return lnumber;
    }

    public void setLnumber(String lnumber) {
        this.lnumber = lnumber;
    }

    @Override
    public String toString() {
        return "Liaison{" +
                "laccount='" + laccount + '\'' +
                ", lno=" + lno +
                ", lnumber='" + lnumber + '\'' +
                ", lname='" + lname + '\'' +
                ", lsex='" + lsex + '\'' +
                ", lphone='" + lphone + '\'' +
                ", lrole='" + lrole + '\'' +
                '}';
    }
}
