package com.user.realm.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.user.realm.entity.Realm;

@Component("realmValidator")
public class RealmValidator implements Validator {

	//which objects can be validated by this validator
	public boolean supports(Class<?> paramClass) {
		return Realm.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		
		if(obj instanceof Realm) {
			Realm realm = (Realm) obj;

			if(!StringUtils.hasLength(realm.getName())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "InvalidRealmName",  new Object[]{"'name'"}, "InvalidRealmName");		
			}			
		}
	}
}