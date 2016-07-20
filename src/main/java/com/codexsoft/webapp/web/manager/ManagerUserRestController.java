package com.codexsoft.webapp.web.manager;

import com.codexsoft.webapp.model.Role;
import com.codexsoft.webapp.model.User;
import com.codexsoft.webapp.service.UserService;
import com.codexsoft.webapp.util.exception.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/manager/users")
public class ManagerUserRestController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId){
        userService.delete(userId);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User get(@PathVariable Long userId){
        return userService.findOne(userId);
    }

    @RequestMapping(value = "/user/save",method = RequestMethod.POST)
    public void createOrUpdate(@RequestBody @Valid User user, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        status.setComplete();
        try {
            user.setRole(Role.ROLE_DEVELOPER);
            user.setIsEnabled(true);
            if (user.getUserId() == null) {
                userService.create(user);
            } else {
                User existing = userService.findOne(user.getUserId());
                copyNonNullProperties(user, existing);
                userService.update(user);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data incorrect!");
        }
    }

    private static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
