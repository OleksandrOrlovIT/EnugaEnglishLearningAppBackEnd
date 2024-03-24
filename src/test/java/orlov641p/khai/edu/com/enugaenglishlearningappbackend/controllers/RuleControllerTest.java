package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.RuleService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RuleControllerTest {

    private static final long ruleId = 1L;

    @Mock
    RuleService ruleService;

    @InjectMocks
    RuleController ruleController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ruleController).build();
    }

    @Test
    void retrieveBrands() throws Exception{
        when(ruleService.findAll())
                .thenReturn(Arrays.asList(Rule.builder().id(1L).build(), Rule.builder().id(2L).build()));

        mockMvc.perform(get("/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveBrandById() throws Exception {
        // Prepare test data
        String tempName = "rulename", temdDescription = "desc";
        Rule expectedRule = Rule.builder().id(ruleId).ruleName(tempName).description(temdDescription).build();
        when(ruleService.findById(ruleId)).thenReturn(expectedRule);

        // Perform the request
        MvcResult result = mockMvc.perform(get("/v1/rule/{id}", ruleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Rule> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        // Assertions
        assertEquals(expectedRule, responseModel.getContent());
    }

    @Test
    void deleteBrand() throws Exception {
        mockMvc.perform(delete("/v1/rule/1")).andExpect(status().is(204));
    }

    @Test
    void updateBrand() throws Exception {
        // Prepare test data
        Rule originalRule = Rule.builder().id(ruleId).ruleName("rule1").description("rule1").build();
        Rule updateRule = Rule.builder().id(ruleId).ruleName("updatedRule").description("UpdatedRule").build();
        when(ruleService.findById(anyLong())).thenReturn(originalRule);
        when(ruleService.save(any())).thenReturn(updateRule);

        // Perform the request
        MvcResult result = mockMvc.perform(put("/v1/rule/{id}", ruleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRule)))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        Rule responseRule = new ObjectMapper().readValue(responseBody, Rule.class);

        // Assertions
        assertNotNull(responseRule);
        assertEquals(updateRule, responseRule);
    }

    @Test
    void createBrand() throws Exception{
        // Prepare test data
        Rule inputRule = Rule.builder().id(null).ruleName("ruleName").build();
        Rule savedRule = Rule.builder().id(ruleId).ruleName("ruleName").build();
        when(ruleService.save(any())).thenReturn(savedRule);

        // Perform the request
        MvcResult result = mockMvc.perform(post("/v1/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputRule)))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify the response
        String locationHeader = result.getResponse().getHeader("Location");

        // Assertions
        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/rule/1", locationHeader);
    }

}