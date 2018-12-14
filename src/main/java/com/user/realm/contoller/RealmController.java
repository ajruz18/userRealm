package com.user.realm.contoller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.user.realm.entity.Realm;
import com.user.realm.service.RealmService;
import com.user.realm.util.RealmUtil;

@RestController
@RequestMapping("/user")
public class RealmController {

    @Autowired
    private RealmService realmService;

	@PostMapping(value = "/realm")
	public ResponseEntity<Realm> setRealm(@Validated(Realm.New.class) @RequestBody Realm realm) {
		
		try {
			realm.setKey(RealmUtil.generateRandomString());
			realm = realmService.addRealm(realm);
		} catch (DataIntegrityViolationException ex){
			return new ResponseEntity(generateMessage("Request processing failed; nested exception is org.springframework.dao.DataIntegrityViolationException"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			return new ResponseEntity(generateMessage("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Realm>(realm, HttpStatus.CREATED);
	}

	@GetMapping(value={"/realm", "/realm/{id}"})
	public ResponseEntity getRealm(@PathVariable(value = "id") Optional<Integer> id) {
		
		if (!id.isPresent()) {
			return new ResponseEntity(generateMessage("InvalidArgument"), HttpStatus.BAD_REQUEST);
		}
		Realm realm = realmService.getRealm(id.get());
		if(realm == null) {
			return new ResponseEntity(generateMessage("RealmNotFound"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(realm, HttpStatus.OK);
	}

//	@GetMapping(value={"/realm", "/realm/{id}"})
//	public ResponseEntity getRealm(@PathVariable Map<String, Integer> pathVariables) {
//		if (!pathVariables.containsKey("id")){
//			return new ResponseEntity(generateMessage("InvalidArgument"), HttpStatus.BAD_REQUEST);
//	    } else {
//	  		Realm realm = realmService.getRealm(pathVariables.get("id"));
//			if(realm == null) {
//				return new ResponseEntity(generateMessage("RealmNotFound"), HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity(realm, HttpStatus.OK);
//	    }
//	}

	@Autowired
	@Qualifier("realmValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleException(MethodArgumentNotValidException exception) {
	    String errorCode = exception.getBindingResult().getFieldError().getCode();
	    return generateMessage(errorCode);
	}

	private String generateMessage(String errorCode) {
		return "<error><code>" + errorCode + "</code></error>";
	}	
	
}
