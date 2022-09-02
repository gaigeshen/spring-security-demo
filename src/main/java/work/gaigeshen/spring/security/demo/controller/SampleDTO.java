package work.gaigeshen.spring.security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author gaigeshen
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SampleDTO {

    private String username;

    private String password;

    private Set<String> authorities;
}
