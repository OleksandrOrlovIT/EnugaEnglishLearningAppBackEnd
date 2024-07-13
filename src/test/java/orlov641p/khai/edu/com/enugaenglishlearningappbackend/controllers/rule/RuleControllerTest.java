package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.rule;

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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.rule.RuleService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RuleControllerTest {

    private static final Long ruleId = 1L;

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
    void retrieveRules() throws Exception{
        when(ruleService.findAll())
                .thenReturn(Arrays.asList(Rule.builder().build(), Rule.builder().build()));

        mockMvc.perform(get("/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveRuleById() throws Exception {
        String tempName = "rulename", tempDescription = "desc";
        Rule expectedRule = Rule.builder()
                .id(ruleId).ruleName(tempName).description(tempDescription)
                .build();

        when(ruleService.findById(ruleId)).thenReturn(expectedRule);

        MvcResult result = mockMvc.perform(get("/v1/rule/{id}", ruleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Rule> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(expectedRule, responseModel.getContent());
    }

    @Test
    void deleteRule() throws Exception {
        mockMvc.perform(delete("/v1/rule/{id}", ruleId)).andExpect(status().is(204));
    }

    @Test
    void updateRule() throws Exception {
        Rule originalRule = Rule.builder().ruleName("rule1").description("rule1").build();
        Rule updateRule = Rule.builder()
                .id(ruleId).ruleName("updatedRule").description("UpdatedRule")
                .build();

        when(ruleService.findById(anyLong())).thenReturn(originalRule);
        when(ruleService.update(any())).thenReturn(updateRule);

        MvcResult result = mockMvc.perform(put("/v1/rule/{id}", ruleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRule)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Rule responseRule = new ObjectMapper().readValue(responseBody, Rule.class);

        assertNotNull(responseRule);
        assertEquals(updateRule, responseRule);
    }

    @Test
    void createRule() throws Exception{
        Rule inputRule = Rule.builder().ruleName("ruleName").build();
        Rule savedRule = Rule.builder().id(ruleId).ruleName("ruleName").build();

        when(ruleService.create(any())).thenReturn(savedRule);

        MvcResult result = mockMvc.perform(post("/v1/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputRule)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/rule/1", locationHeader);
    }

    @Test
    void updateRule_findByIdNull() throws Exception {
        Rule updateRule = Rule.builder()
                .id(ruleId).ruleName("updatedRule").description("UpdatedRule")
                .build();

        when(ruleService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/rule/{id}", ruleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRule)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }
}