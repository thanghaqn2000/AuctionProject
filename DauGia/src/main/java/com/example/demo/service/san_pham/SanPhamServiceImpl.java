package com.example.demo.service.san_pham;

import com.example.demo.model.SanPham;
import com.example.demo.repository.san_pham.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepo sanPhamRepo;

    @Override
    public List<SanPham> findAll() {

        return sanPhamRepo.findAll();
    }

    @Override
    public SanPham findById(int id) {
        return sanPhamRepo.findById(id).orElse(null);
    }

    @Override
    public List<SanPham> findByTinhTrang() {
        return sanPhamRepo.findAllByTinhTrangContaining();
    }


    @Override
    public List<SanPham> Findchuaduyetcuaban(String username) {
        return sanPhamRepo.findAllchuaduyetcuaban(username);
    }

    @Override
    public List<SanPham> findByDaDuyet() {
        return sanPhamRepo.findAllDaDuyet();
    }

    @Override
    public List<SanPham> findByDanhMuc(boolean tinhTrang, int maDanhMuc) {
        return sanPhamRepo.findByTinhTrangAndDanhMuc_MaDanhMucOrderByGiaKhoiDiem(tinhTrang, maDanhMuc);
    }

    @Override
    public List<SanPham> findCuaBan(String isUser) {
        return sanPhamRepo.findCuaBan(isUser);
    }

    @Override
    public List<SanPham> findByName(String tenSp) {
        return sanPhamRepo.findByTenSanPhamContains(tenSp);
    }

    @Override
    public void delete(int id) {
        sanPhamRepo.deleteById(id);
    }

    @Override
    public void create(SanPham sanPham) {
        sanPhamRepo.save(sanPham);
    }



    @Override
    public Page<SanPham> findAllPhanTrang(Pageable pageable) {
        return sanPhamRepo.findAll(pageable);
    }

}
