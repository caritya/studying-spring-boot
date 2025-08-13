package tacos.security;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.User;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public String registerForm(Model model) {
	//	model.addAttribute("userForm", new User());
		model.addAttribute("userForm", new RegistrationForm());
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(@ModelAttribute("userForm") @Valid RegistrationForm form,  BindingResult bindingResult, /*RegistrationForm form,*/ Model model) {
		if (bindingResult.hasErrors()) {
            return "registration";
		}
		User userFromDB = userRepo.findByUsername(form.getUsername());
		if (userFromDB != null) {
			//TODO придумать как выводить ошибку, что такой пользователь уже есть
			model.addAttribute("usernameError","A user with this name already exists");
			return "registration";
		}
		userRepo.save(form.toUser(passwordEncoder));
		return "redirect:/login";
	}


	
	
	/* @PostMapping("/registration")
	    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

	        if (bindingResult.hasErrors()) {
	            return "registration";
	        }
	        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
	            model.addAttribute("passwordError", "Пароли не совпадают");
	            return "registration";
	        }
	        if (!userService.saveUser(userForm)){
	            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
	            return "registration";
	        }

	        return "redirect:/";
	    }*/
	
	
	
}
