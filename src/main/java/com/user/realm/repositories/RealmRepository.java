package com.user.realm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.realm.entity.Realm;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Integer>{

}
