package com.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.Authority;
import com.model.User;


@Service
@Transactional
public class UserService {
	
	@PersistenceContext
	EntityManager em;
	
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@SuppressWarnings("unchecked")
	public String register(User user) {
		
		try{
			List<User> users = (List<User>) em.createQuery("Select u from User u where u.username = :username")
					.setParameter("username", user.getUsername()).getResultList();
			if(users.isEmpty()){
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				user.setEnabled(1);
				em.persist(user);
				
				Authority a = new Authority();
				a.setUsername(user.getUsername());
				a.setAuthority("USER");
				em.persist(a);
				return "Success";
			}
			else {
				return "This username already exists!";
			}
		}catch (Exception e){
			return e.getMessage();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String login(User u){
		
		List<User> users = (List<User>) em.createQuery("Select u from User u where u.username = :username")
				.setParameter("username", u.getUsername()).getResultList();
		
		
		if(users.isEmpty()){
			return "Wrong username!";
		}
		if(!(bCryptPasswordEncoder.matches(u.getPassword(), users.get(0).getPassword()))){
			return "Wrong password!";
		}
		
		return users.get(0).getId().toString();
	}

}
