package com.simple;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.simple.model.User;
import com.simple.service.UserRepository;

import org.hibernate.engine.spi.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringUserDbApplication.class)
public class UserRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    //private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    //private Account account;

    private List<User> userList = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.userRepository.deleteAll();
        
        //to shorten some later expressions
        List<User> ul = this.userList;
        UserRepository ur = this.userRepository;
        
        ul.add(ur.save(new User("Tomasz","Zwierzyński")));
        ul.add(ur.save(new User("Frank","Zappa")));
    }   
    
    
    @Test
    public void userNotFound() throws Exception {
    	mockMvc.perform(get("/api/user/0")          //czy to jest dobrze??
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void readSingleUser() throws Exception {
        mockMvc.perform(get("/api/user/" 
                + this.userList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is((int)this.userList.get(0).getId())))
                .andExpect(jsonPath("$.firstName", is("Tomasz")))
                .andExpect(jsonPath("$.lastName", is("Zwierzyński")));
    }
    
    @Test
    public void readAllUsers() throws Exception {
    	mockMvc.perform(get("/api/user/"))
    			.andExpect(status().isOk())
    			.andExpect(content().contentType(contentType))
    			.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int)this.userList.get(0).getId())))
                .andExpect(jsonPath("$[0].firstName", is("Tomasz")))
                .andExpect(jsonPath("$[0].lastName", is("Zwierzyński")))
                .andExpect(jsonPath("$[1].id", is((int)this.userList.get(1).getId())))
                .andExpect(jsonPath("$[1].firstName", is("Frank")))
                .andExpect(jsonPath("$[1].lastName", is("Zappa")));   	
    }
    
    @Test
    public void createUser() throws Exception {
        String userJson = json(new User(
                "Nowy", "Userek"));

        this.mockMvc.perform(post("/api/user/")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isCreated());
    }    
    
    @Test
    public void deleteUser() throws Exception {
    	mockMvc.perform(delete("/api/user/"
    						+ this.userList.get(0).getId() ))
    			.andExpect(status().isNoContent());
    }
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    
}
