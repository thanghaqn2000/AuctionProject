package com.example.demo.repository.san_pham;

import com.example.demo.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamRepo extends JpaRepository<SanPham, Integer> {
    @Query("select e from  SanPham e where e.tinhTrang = ?1 ")
    List<SanPham> findAllByTinhTrangContaining(String tinhtrang);
//    fale chuwa duyejt

    @Query("select e from  SanPham e where e.tinhTrang = ?1 and e.taiKhoans.taiKhoan= ?2")
    List<SanPham> findAllchuaduyetcuaban(String tinhtrang,String username);
//    false chuwa duyeejt

    @Query("select e from  SanPham e where e.tinhTrang = ?1 ")
    List<SanPham> findAllDaDuyet(String tinhtrang);
//     true - ddax duyeejr

    @Query("select e from  SanPham e where e.taiKhoans.taiKhoan= ?1")
    List<SanPham> findCuaBan(String idUser);

    List<SanPham> findByTinhTrangAndDanhMuc_MaDanhMucOrderByGiaKhoiDiem(String tinhTrang, int maDanhMuc);


    List<SanPham> findByTinhTrangAndDanhMuc_MaDanhMucAndTenSanPhamContainsOrderByGiaKhoiDiem(String tinhTrang, int maDanhMuc, String tenSanPham);

    List<SanPham> findByTenSanPhamContains(String tenSp);

    @Query("select e from  SanPham e where e.taiKhoans.taiKhoan= ?1 and e.tenSanPham like %?2%")
    List<SanPham> findBytensanphamcuaban(String user,String tenSp);

    @Query("select e from  SanPham e where e.tinhTrang = ?1  and e.taiKhoans.taiKhoan = ?2 and e.tenSanPham like %?3%" )
    List<SanPham> findBytensanphamchuaduyetcuaban(String tinhTrang,String user,String tenSp);


    List<SanPham> findByTinhTrangAndTenSanPhamContains(String tinhTrang, String tenSp);
}
