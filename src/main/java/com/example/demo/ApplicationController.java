package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Admin;
import com.example.demo.model.PaySlip;
import com.example.demo.model.User;
import com.example.demo.services.AdminService;
import com.example.demo.services.PaySlipServices;
import com.example.demo.services.UserService;

@Controller
public class ApplicationController {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private PaySlipServices payService;


	// ---------- DEFAULT PAGE ----------
	@RequestMapping("/")
	public String home() {
		return "welcomeemployee";   // ★ MAIN UI PAGE
	}


	// ---------- USER LOGIN ----------
	@RequestMapping("/login-user")
	public String loginUser(@ModelAttribute User user, HttpServletRequest req) {

		if (userService.findByUsernameAndPassword(user.getUsername(), user.getPassword()) != null) {
			return "welcomeemployee";   // ★ After login → show main UI page
		} else {
			req.setAttribute("error", "Invalid Username or Password");
			return "login";
		}
	}


	// ---------- ADMIN LOGIN ----------
	@RequestMapping("/admin")
	public String loginAdmin(@ModelAttribute Admin admin, HttpServletRequest req) {

		if (adminService.findByEmailAndPassword(admin.getEmail(), admin.getPassword()) != null) {
			return "welcomeemployee";   // ★ Admin also goes to main UI
		} else {
			req.setAttribute("error", "Invalid Email or Password");
			return "login";
		}
	}


	@RequestMapping("/register")
	public String registration() {
		return "register";
	}

	@RequestMapping("/save-user")
	public String saveUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest req) {
		userService.saveMyUser(user);
		req.setAttribute("users", userService.showAllUsers());
		return "allusers";
	}

	@RequestMapping("/all-users")
	public String allUsers(HttpServletRequest req) {
		req.setAttribute("users", userService.showAllUsers());
		return "allusers";
	}

	@RequestMapping("/manage")
	public String manage(HttpServletRequest req) {
		req.setAttribute("users", userService.showAllUsers());
		return "manage";
	}

	@RequestMapping("/edit-user")
	public String editUser(@RequestParam String username, HttpServletRequest req) {
		req.setAttribute("user", userService.editUser(username));
		return "updateuser";
	}

	@RequestMapping("/save-payslip")
	public String savePayslip(@ModelAttribute PaySlip payslip, BindingResult bindingresult, HttpServletRequest req,
							  @RequestParam String email, @RequestParam String year,
							  @RequestParam String month, @RequestParam String deduction) {

		req.setAttribute("user", userService.findByEmail(email));
		req.setAttribute("month", month);
		req.setAttribute("year", year);
		req.setAttribute("deduction", deduction);
		payService.savePayslip(payslip);

		return "generate";
	}

	@RequestMapping("/topnotch")
	public String topNotch() {
		return "topnotch";
	}
}
