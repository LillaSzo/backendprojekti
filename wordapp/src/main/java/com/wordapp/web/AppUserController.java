package com.wordapp.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.*;

import com.wordapp.domain.SignupForm;
import com.wordapp.domain.AppUser;
import com.wordapp.domain.AppUserRepository;

@Controller
public class AppUserController {

    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

	@GetMapping("/signup")
	public String addStudent(Model model){
		model.addAttribute("signupform", new SignupForm());
		return "signup";
		}

	@GetMapping("/login")
	public String login() {
		return "login";
		}

	@GetMapping("/userlist")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String userList(Model model){
		model.addAttribute("users", appUserRepository.findByRole("USER"));
		return "userlist";
	}
	@GetMapping("/delete/user/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("id") Long id){
		appUserRepository.deleteById(id);
		return "userlist";
	}

	@PostMapping("/saveuser")
		public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
			if (!bindingResult.hasErrors()) { 
				if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { 		
					String pwd = signupForm.getPassword();
					BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
					String hashPwd = bc.encode(pwd);
		
					AppUser newUser = new AppUser();
					newUser.setPasswordHash(hashPwd);
					newUser.setUsername(signupForm.getUsername());
					newUser.setRole("USER");
					if (appUserRepository.findByUsername(signupForm.getUsername()) == null) {
						appUserRepository.save(newUser);
					}
					else {
						bindingResult.rejectValue("username", "err.username", "Username already exists");    	
						return "signup";		    		
					}
				}
				else {
					bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
					return "signup";
				}
			}
			else {
				return "signup";
			}
			return "redirect:/login";    	
		}   
}
