package com.example.demo.service.tai_khoan;

import com.example.demo.model.Quyen;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaiKhoanQuyenService {
    List<Quyen> finAllQuyen(String taikhoan);

}
