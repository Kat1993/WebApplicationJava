package com.capstone1.homelisting.controler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.capstone1.homelisting.Utils.DataValidation;
import com.capstone1.homelisting.Utils.States;
import com.capstone1.homelisting.Utils.WebUtils;
import com.capstone1.homelisting.model.Users;
import com.capstone1.homelisting.repository.UserRepository;
import com.capstone1.homelisting.service.UserService;

@Controller
@SessionAttributes("LoggedInuser")
public class LoginController {
//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private WebUtils webUtils;
@Autowired
private DataValidation dataValidation; 

@Autowired
private UserService userService;

 @PostMapping("register")
 String register(@ModelAttribute("user") Users user, 
      BindingResult result, 
      RedirectAttributes redirect) {

    try {
       dataValidation.validate(user, result);
       if (result.hasErrors()) {
    return "register";
        }
    user.setRole("USER");
    userService.save(user);
    redirect.addFlashAttribute("msg", "Registration success");
} catch (Exception e) {
     e.printStackTrace();
}

return "redirect:/login";

	}
	
	@GetMapping("login")
	String login() {
		return"login";
	}

	@GetMapping("profile")
	String profile() {		
	return "profile";		
	}

	@GetMapping("logout")
	String logout(SessionStatus status, Model model) {
		status.setComplete();
		model.addAttribute("LoggedInuser", "");
		model.addAttribute("msg", "Have a good day");
		return"login";
	}
	@PostMapping("login")
	String login(@RequestParam String email,
			@RequestParam String password, Model model) {
	 Users user =	userService.login(email, password);
	if(user!=null ) {
		model.addAttribute("msg", "Welcome to your page");
		model.addAttribute("LoggedInuser", user);
		return "profile";
	}else {
		model.addAttribute("error", "Invalid credentiol");
	}	
		 return "login";
	}
	
@GetMapping("register")
String register(Model model) {
	try {
		model.addAttribute("user", new Users());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "register";
	
}
@GetMapping("users")
String users(Model model) {
	

		try {
			model.addAttribute("users",userService.findAll() );
			model.addAttribute("msg","All ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	return "users";
	
}
@PostMapping("search")
 String search(@RequestParam String name, Model model) {
	model.addAttribute("users", userService.searchByname(name));
	model.addAttribute("msg", " found "+ name);
	return"users";
}

@GetMapping("delete")
String delete(@RequestParam Long id, RedirectAttributes redirect) {

    try {
   userService.delete(id);
  redirect.addFlashAttribute("success", "Delete Success");
  } catch (Exception e) {
  e.printStackTrace();
  redirect.addFlashAttribute("success", "Delete Fail");
}

   return "redirect:/users";
}

   @PostMapping("editrole")
   String editrole(@RequestParam Long id,@RequestParam String role, RedirectAttributes redirect) {

try {
	userService.findById(id).ifPresent(user->{
	user.setRole(role);
	userService.save(user);
        });;
     redirect.addFlashAttribute("success", role + "Role Granted");
    } catch (Exception e) {
    e.printStackTrace();
    redirect.addFlashAttribute("success", "Operation Fail");
      }

      return "redirect:/users";
  }
   @PostMapping("update")
    String update(@ModelAttribute Users user , Model model) {

	   userService.findById(user.getId()).ifPresent(a->{
		   a.setAddress(user.getAddress());
		   a.setCity(user.getCity());
		   a.setState(user.getState());
		   a.setFirstName(user.getFirstName());
		   a.setLastName(user.getLastName());
		   a.setPhone(user.getPhone());
		   userService.save(a);
		   model.addAttribute("LoggedInuser", a);
		  
	   });;
	      
	   model.addAttribute("msg", "Success");
	   return "redirect:/profile";
	   
   }
   @ModelAttribute("states")
   public List<States> populateStates(){   
       return Arrays.asList(States.values());
   }
   @PostMapping("/addimages")
   public String add(@RequestParam("file") MultipartFile file, 
   @RequestParam Long id, Model model) {
           
   Pattern ext = Pattern.compile("([^\\s]+(\\.(?i)(png|jpg))$)");
   try {

     if(file != null && file.isEmpty()){
     model.addAttribute("error", "Error No file Selected "); 
         return "redirect:profile"; 
         } 
     if(file.getSize()>1073741824){
     model.addAttribute("error","File size "+file.getSize()+"KB excceds max allowed, try another photo ");
     return "redirect:profile"; 
         } 
     Matcher mtch = ext.matcher(file.getOriginalFilename());
    
     if (!mtch.matches()) {
     model.addAttribute("error", "Invalid Image type "); 
         return "redirect:profile";  
     }

   //save image
   webUtils.addProfilePhoto(file, id, "users");
   model.addAttribute("msg", "Upload success "+ file.getSize()+" KB");
   model.addAttribute("LoggedInuser", userService.findById(id).get());
   model.addAttribute("_profile", "active");
   } catch (Exception e) {
   //e.printStackTrace);
   }

   return "profile";
   }
   @PostMapping("addcomment")
   String addcomment(@RequestParam String comment, 
   @RequestParam Long id,
   Model model) {
	   userService.findById(id).
   ifPresent(a->{
   a.setComment(comment);
   userService.save(a);
   model.addAttribute("loggedInuser",a);
   });
   model.addAttribute("msg", "Update success");
   return "redirect:profile";
   }
}


