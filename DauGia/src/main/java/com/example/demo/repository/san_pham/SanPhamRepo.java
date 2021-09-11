package com.example.demo.repository.san_pham;

import com.example.demo.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamRepo extends JpaRepository<SanPham, Integer> {
    @Query("select e from  SanPham e where e.tinhTrang = false ")
    List<SanPham> findAllByTinhTrangContaining();

    @Query("select e from  SanPham e where e.tinhTrang = false and e.taiKhoans.taiKhoan= ?1")
    List<SanPham> findAllchuaduyetcuaban(String username);

    @Query("select e from  SanPham e where e.tinhTrang = true ")
    List<SanPham> findAllDaDuyet();

    @Query("select e from  SanPham e where e.taiKhoans.taiKhoan= ?1")
    List<SanPham> findCuaBan(String idUser);

    List<SanPham> findByTinhTrangAndDanhMuc_MaDanhMucOrderByGiaKhoiDiem(boolean tinhTrang, int maDanhMuc);

    List<SanPham> findByTinhTrangAndDanhMuc_MaDanhMucOrTenSanPhamContainsOrderByGiaKhoiDiem(boolean tinhTrang, int maDanhMuc, String tenSanPham);

    List<SanPham> findByTenSanPhamContains(String tenSp);

    @Query("select e from  SanPham e where e.taiKhoans.taiKhoan= ?1 and e.tenSanPham like %?2%")
    List<SanPham> findBytensanphamcuaban(String user,String tenSp);

    List<SanPham> findByTinhTrangAndTenSanPhamContains(boolean tinhTrang, String tenSp);
}
