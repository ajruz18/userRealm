package com.user.realm.contoller;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.user.realm.entity.UserRealm;
import com.user.realm.service.RealmService;
import com.user.realm.util.RealmUtil;
import com.user.realm.validator.Error;

@RestController
@RequestMapping("/user")
public class RealmController {

	private static Logger log = Logger.getLogger(RealmController.class.getName());
	
    @Autowired
    private RealmService realmService;

	@PostMapping(value = "/realm"
			, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<UserRealm> setXMLRealm(@Validated(UserRealm.New.class) @RequestBody UserRealm userRealm) {

		try {
			userRealm.setKey(RealmUtil.generateRandomString());
			userRealm = realmService.addUserRealm(userRealm);
		
		} catch (DataIntegrityViolationException ex){
			return new ResponseEntity(new Error("DuplicateRealmName"), HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity(new Error("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity(userRealm, HttpStatus.CREATED);
	}

	@GetMapping(value={"/realm", "/realm/{id}"}
	, produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public ResponseEntity<UserRealm> getJSONRealm(@PathVariable(value = "id") Optional<Integer> id) {
		
		if (!id.isPresent()) {
			return new ResponseEntity(new Error("InvalidArgument"), HttpStatus.BAD_REQUEST);
		}
		UserRealm userRealm = realmService.getUserRealm(id.get());
		if(userRealm == null) {
			return new ResponseEntity(new Error("RealmNotFound"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(userRealm, HttpStatus.OK);
	}

	@Autowired
	@Qualifier("realmValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handleException(MethodArgumentNotValidException exception) {
	    String errorCode = exception.getBindingResult().getFieldError().getCode();
	    return new Error(errorCode);
	}

}
