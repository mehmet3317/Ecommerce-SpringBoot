package com.ecommerce.admin.controller;

import com.ecommerce.libraryy.dto.AdminDto;
import com.ecommerce.libraryy.model.Admin;
import com.ecommerce.libraryy.service.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/login")
    public String loginForm(){
        return "Login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes){
        try{
            if(result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                redirectAttributes.addFlashAttribute("message","Your email has been registered");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminService.save(adminDto);
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","registered successfully");
            }else{
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","password is not same");
                return "register";
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message","Can not registered because error server");
        }

        return "register";
    }
}
