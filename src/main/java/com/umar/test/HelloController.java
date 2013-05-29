package com.umar.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	private static final String VIEW_DISH_FORM = "/WEB-INF/add_dish_form.jsp";
	private static final String VIEW_DISH_FORM_SUCCESS = "/WEB-INF/add_dish_form_success.jsp";
	private static final String VIEW_DISH_LIST = "/WEB-INF/dishes.jsp";
	
	@Autowired
	private DataSource dataSource;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	@RequestMapping(value={"/hello", "/"})
	public String sayHello(@RequestParam(required=false, defaultValue="X", value="firstName") String name, @RequestParam String city, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("city", city);
		return "/WEB-INF/hello.jsp";		
	}
	
	@RequestMapping("/persons/{name}")
	public String test2(@PathVariable String name, Model model, @RequestParam Date date) throws ParseException {
		// dd-mm-yyyy
		
		System.out.println(date);
		
		model.addAttribute("name", name);
		return "/WEB-INF/hello.jsp";
	}
	
	@RequestMapping("/dishes")
	public String showDishes(Model model) {
		model.addAttribute("dishes", fetchAllDishes());
		return VIEW_DISH_LIST;
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.GET)
	public String getDishForm(Model model) {
		model.addAttribute("command", new Dish());
		return VIEW_DISH_FORM;
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.POST)
	public String postDishForm(@ModelAttribute("command") @Valid Dish dish, BindingResult result,  Model model) {
		if ( result.hasErrors() ) {
			result.reject(null, "There was an error in the form");
			return VIEW_DISH_FORM;
		}
		
		addDish(dish);
		
		model.addAttribute("dish", dish);
		return "redirect:/dishes";
	}
	
	private void addDish(Dish dish) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO dishes (title, cost, price, description) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setObject(1, dish.getTitle());
			ps.setObject(2, dish.getCost());
			ps.setObject(3, dish.getPrice());
			ps.setObject(4, dish.getDescription());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if ( rs.next() ) {
				dish.setId(rs.getInt(1));
			}
			
			ps.close();
			conn.close();
			
		} catch( Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Dish> fetchAllDishes() {
		List<Dish> dishes = new ArrayList<Dish>();
		
		try {
			Connection conn = getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM dishes");
			
			while ( rs.next() ) {
				dishes.add(new Dish(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
			}
			
			s.close();
			conn.close();
			
		} catch( Exception e ) {
			throw new RuntimeException(e);
		}
		
		return dishes;
	}
	
	private Connection getConnection() {
		try { 
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection c = DriverManager.getConnection("jdbc:mysql://localhost/restaurant_db", "root", "");
//			return c;
			
			return dataSource.getConnection();
		} catch ( Exception e ) {
			throw new RuntimeException(e);		
		}
	}
}
