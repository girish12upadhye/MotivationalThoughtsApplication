package org.girishtechuniverse.controller;

import java.util.Map;

import org.girishtechuniverse.bindings.LoginForm;
import org.girishtechuniverse.bindings.RegisterForm;
import org.girishtechuniverse.bindings.ResetPwdForm;
import org.girishtechuniverse.constants.AppConstants;
import org.girishtechuniverse.entity.User;
import org.girishtechuniverse.props.AppProps;
import org.girishtechuniverse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppProps appProps;

	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("login", new LoginForm());

		return "index";
	}

	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("login") LoginForm login, Model model) {

		User user = userService.login(login);

		if(user == null) {

			model.addAttribute(AppConstants.ERROR_MSG, appProps.getMessages().get("invalidLogin"));
			return "index";
		}

		if(user.getPwdUpdated().equals("NO")) {

			ResetPwdForm formObj = new ResetPwdForm();
			formObj.setUserId(user.getUserId());

			model.addAttribute("resetPwd", formObj);
			return "resetPwd";
		}

		return "redirect:dashboard";
	}

	@PostMapping("/updatePwd")
	public String updatePwd(@ModelAttribute("resetPwd") ResetPwdForm resetPwd, Model model) {


		if(!resetPwd.getNewPwd().equals(resetPwd.getConfirmPwd())){
			model.addAttribute("errmsg", appProps.getMessages().get("invalidPwds"));
			return "resetPwd";
		}

		boolean status = userService.resetPwd(resetPwd);

		if(status) {
			return "redirect:dashboard";
		}

		model.addAttribute("errmsg", appProps.getMessages().get("pwdUpdatedFailed"));

		return "resetPwd";
	}

	@GetMapping("/register")
	public String loadRegistrationPage(Model model) {

		model.addAttribute("registerForm", new RegisterForm());

		Map<Integer,String> countries = userService.getCountries();

		model.addAttribute("countries", countries);

		return "register";
	}

	@GetMapping("/getStates")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") Integer countryId){

		return userService.getStates(countryId);
	}

	@GetMapping("/getCities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer stateId){

		return userService.getCities(stateId);
	}

	@PostMapping("/register")
	public String saveUser(@ModelAttribute RegisterForm registerForm, Model model) {

		boolean status = userService.saveUser(registerForm);

		if(status) {
			model.addAttribute("succMsg", appProps.getMessages().get("regSuccess"));
		}
		else {
			model.addAttribute("errMsg", appProps.getMessages().get("regFailed"));
		}

			Map<Integer,String> countries = userService.getCountries();

			model.addAttribute("countries", countries);
		return "register";
	}
}
