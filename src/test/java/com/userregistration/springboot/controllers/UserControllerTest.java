package com.userregistration.springboot.controllers;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.userregistration.springboot.model.Role;
import com.userregistration.springboot.model.User;
import com.userregistration.springboot.repository.RoleRepository;
import com.userregistration.springboot.repository.UserRepository;
import com.userregistration.springboot.service.RoleService;
import com.userregistration.springboot.service.SecurityService;
import com.userregistration.springboot.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServiceImpl userService;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RoleRepository roleRepository;
	
	@MockBean
	private RoleService roleService;
	
	@MockBean
	private SecurityService securityService;
	
	String userRequestJson = "{\"username\":\"prajnakarkal\",\"password\":\"prajnak\",\"emailId\":\"prajnak@gmail.com\",	\"roles\": [{	\"id\":\"1\",\"name\": \"admin\"}]}";
	
	@Test
	public void createUser() throws Exception {
		User user = getUserObject();

		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registration")
				.accept(MediaType.APPLICATION_JSON).content(userRequestJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}


    @Test
    public void getUser() throws Exception {
    	User user = getUserObject();
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    	   	
    	Date currentDate = new Date();
    	user.setCreatedAt(currentDate);
		user.setUpdatedAt(currentDate);
		
		String currentDateString = simpleDateFormat.format(currentDate).replaceAll("Z$", "+0000");
		
    	Mockito.when(
    			userService.findByUsername(Mockito.anyString())).thenReturn(user);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/registration/prajnakarkal").accept(MediaType.APPLICATION_JSON);
    	
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		
		String expected = "{\"id\": 1,\"username\": \"prajnakarkal\",\"emailId\": \"prajnak@gmail.com\","
				+ "\"createdAt\": \""+currentDateString+"\",\"updatedAt\": \""+currentDateString+"\",\"roles\": [\r\n" + 
				"        {\"id\": 1,\"name\": \"admin\"}]}";
		
		System.out.println("expected :"+expected);
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    } 


	private User getUserObject() throws ParseException {
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setId(Long.valueOf(1));
		role.setName("admin");
		roles.add(role);
		
		User user = new User();
		user.setId(Long.valueOf(1));
		user.setUsername("prajnakarkal");
		user.setPassword("prajnak");
		user.setEmailId("prajnak@gmail.com");
		user.setRoles(roles);
		
		return user;
	}

}
