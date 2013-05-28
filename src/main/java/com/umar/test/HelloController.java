package com.umar.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	private static final String VIEW_DISH_FORM = "/WEB-INF/add_dish_form.jsp";
	private static final String VIEW_DISH_FORM_SUCCESS = "/WEB-INF/add_dish_form_success.jsp";
	
	@RequestMapping(value={"/hello", "/"})
	public String sayHello(@RequestParam(required=false, defaultValue="X", value="firstName") String name, @RequestParam String city, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("city", city);
		return "/WEB-INF/hello.jsp";		
	}
	
	@RequestMapping("/persons/{name}")
	public String test2(@PathVariable String name, Model model) {
		model.addAttribute("name", name);
		return "/WEB-INF/hello.jsp";
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.GET)
	public String getDishForm(Model model) {
		model.addAttribute("command", new Dish());
		return VIEW_DISH_FORM;
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.POST)
	public String postDishForm(@ModelAttribute("command") Dish dish, BindingResult result,  Model model) {
		System.out.println(dish);
		model.addAttribute("dish", dish);
		return "redirect:/persons/Umar";
	}
}
