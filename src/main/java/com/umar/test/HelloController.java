package com.umar.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	
	@RequestMapping(value={"/hello", "/"})
	public String sayHello(@RequestParam(required=false, defaultValue="X", value="firstName") String name, @RequestParam String city, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("city", city);
		return "/WEB-INF/hello.jsp";
	}
}
