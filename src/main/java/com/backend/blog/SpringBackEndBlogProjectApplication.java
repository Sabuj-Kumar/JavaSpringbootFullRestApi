package com.backend.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entity.Role;
import com.backend.blog.repositories.RoleRepository;

@SpringBootApplication
public class SpringBackEndBlogProjectApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBackEndBlogProjectApplication.class, args);
	}
	
	@Autowired	
    RoleRepository roleRepo;

	@Bean
	public CommandLineRunner demo() {
	      return (args) -> {

              try {
            	  Role role = new Role();
            	  role.setId(AppConstants.ROLE_ADMIN); 
            	  role.setRoleName("ROLE_ADMIN");
            	  
            	  Role role1 = new Role();
            	  role1.setId(AppConstants.ROLE_USER); 
            	  role1.setRoleName("ROLE_USER");
            	  
            	  List<Role> roles = List.of(role,role1);
            	  
            	  List<Role> result = this.roleRepo.saveAll(roles);
            	  
            	  result.forEach(r->{
            		  
            		  System.out.println(r.getRoleName());
            	  });
            	  
              }
              catch(Exception e) {
            	  
              }
	      };
	   }


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
