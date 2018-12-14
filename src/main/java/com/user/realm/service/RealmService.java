package com.user.realm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.realm.entity.Realm;
import com.user.realm.repositories.RealmRepository;

@Service
public class RealmService {

	@Autowired
	private RealmRepository realmRepository;
	
	public Realm addRealm(Realm realm) {
		return realmRepository.save(realm);
	}

	public Realm getRealm(Integer id) {
		Optional<Realm> optional = realmRepository.findById(id);
		if(optional.isPresent())
			return optional.get();
		return null;
	}

}
