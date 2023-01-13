package com.example.demo.common.utils;

import com.example.demo.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CommonUtils {

    public static Object transformData(Object input, Object output){
        Arrays.stream(input.getClass().getDeclaredFields())
                .forEach(field -> {
                    String outputSetterMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    field.setAccessible(true);
                    try {
                        Method declaredMethod = output.getClass()
                                .getDeclaredMethod(outputSetterMethod, field.getType());

                        declaredMethod.invoke(output, field.get(input));
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        log.warn("unable to set value for field {}  into output object", field.getName());
                    }
                });

        return output;
    }

    public static Collection<? extends GrantedAuthority> getAuthoritiesFromRoles(String commaSeparatedRolesString) {
        return Arrays.stream(commaSeparatedRolesString.split(","))
                .map(SimpleGrantedAuthority::new).toList();
    }

    public static Collection<? extends GrantedAuthority> getAuthoritiesFromRoles(List<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .map(SimpleGrantedAuthority::new).toList();
    }

}
