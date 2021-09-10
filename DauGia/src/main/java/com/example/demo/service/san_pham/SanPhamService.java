package com.example.demo.service.san_pham;

import com.example.demo.model.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SanPhamService {
    List<SanPham> findAll();
    SanPham findById(int id);
    void delete(int id);
    void create(SanPham sanPham);


    List<SanPham> findByTinhTrang();
    List<SanPham> Findchuaduyetcuaban(String username);

    List<SanPham> findByDaDuyet();
    List<SanPham> findByDanhMuc(boolean tinhTrang,int maDanhMuc);

    //sản phẩm của người dùng
    List<SanPham> findCuaBan( String idUser);
    List<SanPham> findByName(String tenSp);
    Page<SanPham> findAllPhanTrang(Pageable pageable);

}
