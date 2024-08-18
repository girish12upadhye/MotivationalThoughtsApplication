package org.girishtechuniverse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.girishtechuniverse.bindings.LoginForm;
import org.girishtechuniverse.bindings.RegisterForm;
import org.girishtechuniverse.bindings.ResetPwdForm;
import org.girishtechuniverse.entity.City;
import org.girishtechuniverse.entity.Country;
import org.girishtechuniverse.entity.State;
import org.girishtechuniverse.entity.User;
import org.girishtechuniverse.props.AppProps;
import org.girishtechuniverse.repository.CityRepo;
import org.girishtechuniverse.repository.CountryRepo;
import org.girishtechuniverse.repository.StateRepo;
import org.girishtechuniverse.repository.UserRepo;
import org.girishtechuniverse.utils.EmailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailsUtils emailUtils;

	@Autowired
	private AppProps appProps;
	
	 Random random = new Random();

	@Override
	public Map<Integer, String> getCountries() {

		List<Country> countriesList = countryRepo.findAll();

		Map<Integer, String> countriesMap = new HashMap<>();

		countriesList.forEach(c -> {
			countriesMap.put(c.getCountryId(), c.getCountryName());
		});
		return countriesMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		Map<Integer, String> statesMap = new HashMap<>();

		 List<State> statesList = stateRepo.findByCountryId(countryId);

		 statesList.forEach(s ->{
			 statesMap.put(s.getStateId(), s.getStateName());
		 });

		 return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		Map<Integer, String> citiesMap = new HashMap<>();

		 List<City> citiesList = cityRepo.findByStateId(stateId);

		 citiesList.forEach(c -> {
			 citiesMap.put(c.getCityId(), c.getCityName());
		 });

		return citiesMap;
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean saveUser(RegisterForm formObj) {

		String initPwd = generateRandomPwd(8);

		formObj.setPwd(initPwd);
		formObj.setPwdUpdated("NO");

		User user = new User();

		BeanUtils.copyProperties(formObj, user);

		userRepo.save(user);


		String subject = appProps.getMessages().get("regMailSubject");
		String body = "<h3>Your Password : "+formObj.getPwd()+"</h3>";

		return emailUtils.sendEmail(subject, body, formObj.getEmail());
	}

	private String generateRandomPwd(int length) {

		  String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

		    StringBuffer randomString = new StringBuffer(length);

		    for (int i = 0; i < length; i++) {
		        int randomIndex = random.nextInt(alphanumericCharacters.length());
		        char randomChar = alphanumericCharacters.charAt(randomIndex);
		        randomString.append(randomChar);
		    }

		    return randomString.toString();

	}

	@Override
	public User login(LoginForm formObj) {

		return userRepo.findByEmailAndPwd(formObj.getEmail(), formObj.getPwd());
	}

	@Override
	public boolean resetPwd(ResetPwdForm formObj) {

		Optional<User> findById = userRepo.findById(formObj.getUserId());

		if(findById.isPresent()) {
			User user = findById.get();
			user.setPwd(formObj.getNewPwd());
			user.setPwdUpdated("YES");
			userRepo.save(user);

			return true;
		}
		return false;
	}

}
