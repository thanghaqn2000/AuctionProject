package com.example.demo.service.tai_khoan;

import com.example.demo.model.Quyen;
import com.example.demo.model.TaiKhoan;
import com.example.demo.repository.nguoi_dung.TaiKhoanQuyenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanQuyenServicelmpl implements  TaiKhoanQuyenService {

    @Autowired
    private TaiKhoanQuyenRepo taiKhoanQuyenRepo;

    @Override
    public List<Quyen> finAllQuyen(String taikhoan)
    {
        return taiKhoanQuyenRepo.findAlQuyen(taikhoan);
    }


}
