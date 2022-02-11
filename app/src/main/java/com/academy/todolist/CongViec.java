package com.academy.todolist;

public class CongViec {

    private int idCv;
    private String tenCv;

    public CongViec(int idCv, String tenCv) {
        this.idCv = idCv;
        this.tenCv = tenCv;
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

    public String getTenCv() {
        return tenCv;
    }

    public void setTenCv(String tenCv) {
        this.tenCv = tenCv;
    }
}
