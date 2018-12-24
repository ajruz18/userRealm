package com.user.realm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.realm.entity.UserRealm;
import com.user.realm.repositories.RealmRepository;

@Service
public class RealmService {

	@Autowired
	private RealmRepository realmRepository;
	
	@Transactional
	@CachePut(cacheNames="userRealm")
	public UserRealm addUserRealm(UserRealm userRealm) {
		return realmRepository.save(userRealm);
	}

	@Cacheable("userRealm")
	public UserRealm getUserRealm(Integer id) {
		Optional<UserRealm> optional = realmRepository.findById(id);
		if(optional.isPresent())
			return optional.get();
		return null;
	}

}
