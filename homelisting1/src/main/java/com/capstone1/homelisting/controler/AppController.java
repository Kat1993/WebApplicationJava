package com.capstone1.homelisting.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.capstone1.homelisting.Utils.WebUtils;
import com.capstone1.homelisting.model.Users;
import com.capstone1.homelisting.repository.UserRepository;

@Controller
//@RequestMapping("/api")
public class AppController {
	
@Autowired
private UserRepository userRepository;
	@Autowired
	private WebUtils webUtils;
	
	@GetMapping({"/","/index"})
	String index(Model model) {
	model.addAttribute("_index", "active");
	
	model.addAttribute("comments",userRepository.findAll() );
	return "index";
	}

	@GetMapping("/about")
	String about(Model model) {
	model.addAttribute("_about", "active");
	model.addAttribute("msg", "This is about us page");
	return "about";
	}

	@GetMapping("/services")
	String service(Model model) {
	  model.addAttribute("_services", "active");
	  model.addAttribute("message", "Services");
	return "services";
	}

	@GetMapping("/contact")
	String contact(Model model) {
	  model.addAttribute("_contact", "active");
	  //model.addAttribute("message", "Services");
	return "contact";
	}
	@PostMapping("sendemail")
	String sendemail(@RequestParam String email, 
	@RequestParam String name,
	@RequestParam String subject,
	@RequestParam String message, Model model) {
	try {
	webUtils.sendMail(email, message+" From "+ name, subject);
	model.addAttribute("msg", "Your message has been sent. Thank you! "+ name);
	} catch (Exception e) {
	e.printStackTrace();
	model.addAttribute("msg", "Email fail! "+ name);

	}
	 
	return "contact";
	}

	@PostMapping("/signup")
	String signup(@RequestParam String firstName,@RequestParam String lastName, Model model) {
		model.addAttribute("msg", "My first Name " +firstName+"<br>"+" My last Name " + lastName);

			return "index";
	}
	@GetMapping ("name")
	String name(@RequestParam String id,@RequestParam String dob, Model model) {
	model.addAttribute("msg", "Kto ti " +id+" Day of birth " + dob);

		return "index";
	}
	
}
