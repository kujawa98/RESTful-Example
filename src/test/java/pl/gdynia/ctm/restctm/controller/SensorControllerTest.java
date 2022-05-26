package pl.gdynia.ctm.restctm.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SensorControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public SensorControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void mockVvcWiringTest() {
        assertThat(mockMvc).isNotNull();
    }


    @Test
    public void postSensorTest() throws Exception {
        clearRepo();
        var responseBody = "{\"id\":0,\"_links\":{\"Sensor_0\":{\"href\":\"http://localhost/sensor/0\"}},\"_links\":{\"self\":{\"href\":\"http://localhost/sensor\"}}}";
        mockMvc.perform(MockMvcRequestBuilders.post("/sensor"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    @Order(1)
    public void getSensorV1Test() throws Exception {
        var responseBody = "{\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/sensor\"}],\"content\":[]}";
        var responseContentType = "application/vnd.ctm.v1+json";
        mockMvc.perform(MockMvcRequestBuilders.get("/sensor"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", responseContentType));
    }

    @Test
    @Order(2)
    public void getSensorV2Test() throws Exception {
        var responseBody = "{\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/sensor\"}],\"content\":[]}";
        var responseContentType = "application/vnd.ctm.v2+json";
        mockMvc.perform(MockMvcRequestBuilders.get("/sensor").accept(responseContentType))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", responseContentType));
    }

    @Test
    public void multiplePostsTest() throws Exception {
        var randomNumber = (new Random()).nextInt(11);
        var responseBody = "{\"id\":%d,\"_links\":{\"Sensor_%d\":{\"href\":\"http://localhost/sensor/%d\"}},\"_links\":{\"self\":{\"href\":\"http://localhost/sensor\"}}}";
        clearRepo();
        for (int i = 0; i < randomNumber; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/sensor"))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(responseBody.formatted(i, i, i)));
            ;
        }
    }

    @Test
    public void deleteSingleTest() throws Exception {
        clearRepo();
        addOne();
        var responseBody = "{\"_links\":{\"self\":{\"href\":\"http://localhost/sensor\"}}}";
        mockMvc.perform(MockMvcRequestBuilders.delete("/sensor/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(responseBody));
    }

    @Test
    public void deleteMultipleTest() throws Exception {
        var randomNumber = (new Random()).nextInt(11);
        for (int i = 0; i < randomNumber; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/sensor"));
        }
        for (int i = 0; i < randomNumber; i++) {
            var resultBody = getMultipleResultString(i + 1);
            mockMvc.perform(MockMvcRequestBuilders.delete("/sensor/%d".formatted(i)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(resultBody));
        }
    }

    private String getMultipleResultString(int i) {
        var prefix = "{\"_embedded\":{\"nodeList\":[";
        var postfix = "]},\"_links\":{\"self\":{\"href\":\"http://localhost/sensor\"}}}";
        var partTestStr = "{\"%d\":{\"id\":%d,\"links\":[{\"rel\":\"Sensor_%d\",\"href\":\"http://localhost:8080/sensor/%d\"}]}}";
        var result = new StringBuilder();
        result.append(prefix);
        for (int j = 0; j < i - 1; j++) {
            result.append(partTestStr.formatted(j, j, j, j));
            result.append(",");
        }
        result.append(partTestStr.formatted(i - 1, i - 1, i - 1, i - 1));
        result.append(postfix);
        return result.toString();
    }


    private void clearRepo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/sensor"));
    }

    private void addOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/sensor"));
    }
}