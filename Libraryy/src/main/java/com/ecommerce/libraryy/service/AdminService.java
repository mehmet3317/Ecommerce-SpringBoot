package com.ecommerce.libraryy.service;

import com.ecommerce.libraryy.dto.AdminDto;
import com.ecommerce.libraryy.model.Admin;

public interface AdminService {

    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
