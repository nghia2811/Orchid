package com.project2.orchid.Object;

public class Product {

    private String Ten, HinhAnh, DanhMuc, NhaSanXuat, ThuơngHieu, XuatXu, Mota, GiaTien, NguoiBan;

    public Product() {
    }

    public Product(String ten, String hinhAnh, String giaTien, String danhMuc, String nhaSanXuat, String thuơngHieu, String xuatXu, String mota, String nguoiBan) {
        Ten = ten;
        HinhAnh = hinhAnh;
        DanhMuc = danhMuc;
        NhaSanXuat = nhaSanXuat;
        ThuơngHieu = thuơngHieu;
        XuatXu = xuatXu;
        Mota = mota;
        GiaTien = giaTien;
        NguoiBan = nguoiBan;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getDanhMuc() {
        return DanhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        DanhMuc = danhMuc;
    }

    public String getNhaSanXuat() {
        return NhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        NhaSanXuat = nhaSanXuat;
    }

    public String getThuơngHieu() {
        return ThuơngHieu;
    }

    public void setThuơngHieu(String thuơngHieu) {
        ThuơngHieu = thuơngHieu;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String xuatXu) {
        XuatXu = xuatXu;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(String giaTien) {
        GiaTien = giaTien;
    }

    public String getNguoiBan() {
        return NguoiBan;
    }

    public void setNguoiBan(String nguoiBan) {
        NguoiBan = nguoiBan;
    }
}
