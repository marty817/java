
package com.example.busapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void registration_login_and_me_and_topup_and_buy_flow() throws Exception {
        // Register a fresh user with 50.00 credit
        var registerReq = Map.of("email","test1@example.com","password","pass123","credit", new BigDecimal(\"50.00\"));
        var regMvc = mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerReq)))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode regBody = mapper.readTree(regMvc.getResponse().getContentAsString());
        Integer userId = regBody.get("id").intValue();
        assertThat(regBody.get("email").asText()).isEqualTo("test1@example.com");

        // Login
        var loginReq = Map.of("email","test1@example.com","password","pass123");
        var loginMvc = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode loginBody = mapper.readTree(loginMvc.getResponse().getContentAsString());
        String token = loginBody.get("token").asText();
        assertThat(token).isNotBlank();

        // /me should return user info and credit 50.00
        var meMvc = mvc.perform(get("/me").header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode meBody = mapper.readTree(meMvc.getResponse().getContentAsString());
        assertThat(meBody.get("email").asText()).isEqualTo("test1@example.com");
        assertThat(new BigDecimal(meBody.get("credit").asText())).isEqualByComparingTo(new BigDecimal(\"50.00\"));

        // List trips (should include preloaded trips)
        var tripsMvc = mvc.perform(get("/trips")).andExpect(status().isOk()).andReturn();
        JsonNode tripsBody = mapper.readTree(tripsMvc.getResponse().getContentAsString());
        assertThat(tripsBody.isArray()).isTrue();
        assertThat(tripsBody.size()).isGreaterThanOrEqualTo(2);

        // Find a trip with price 15.50 (preloaded Rome-Florence)
        Integer tripId = null;
        BigDecimal tripPrice = null;
        for (JsonNode t : tripsBody) {
            if (t.has(\"price\") && t.get(\"price\").asText().equals(\"15.50\")) {
                tripId = t.get(\"id\").intValue();
                tripPrice = new BigDecimal(t.get(\"price\").asText());
                break;
            }
        }
        // If not found, just pick first
        if (tripId == null) {
            tripId = tripsBody.get(0).get(\"id\").intValue();
            tripPrice = new BigDecimal(tripsBody.get(0).get(\"price\").asText());
        }

        // Buy trip - should succeed (credit 50 >= price)
        var buyMvc = mvc.perform(post("/trips/"+tripId+"/buy")
                .header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode buyBody = mapper.readTree(buyMvc.getResponse().getContentAsString());
        assertThat(buyBody.get("userId").intValue()).isEqualTo(userId);
        assertThat(buyBody.get("tripId").intValue()).isEqualTo(tripId);
        BigDecimal charged = new BigDecimal(buyBody.get("charged").asText());
        BigDecimal remaining = new BigDecimal(buyBody.get("remainingBalance").asText());
        assertThat(charged).isEqualByComparingTo(tripPrice);
        assertThat(remaining).isEqualByComparingTo(new BigDecimal(\"50.00\").subtract(tripPrice));

        // Now top-up using /me/credit/toup by 10.00
        var topReq = Map.of("amount", new BigDecimal(\"10.00\"));
        var topMvc = mvc.perform(patch("/me/credit/toup").header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(topReq)))
                .andExpect(status().isOk()).andReturn();
        JsonNode topBody = mapper.readTree(topMvc.getResponse().getContentAsString());
        assertThat(new BigDecimal(topBody.get("credit").asText())).isEqualByComparingTo(remaining.add(new BigDecimal(\"10.00\")));

        // Attempt to buy an expensive trip with insufficient credit:
        // create a very expensive trip as admin, then try to buy with this user
        // Login as admin (preloaded admin@example.com/adminpass)
        var adminLogin = Map.of("email","admin@example.com","password","adminpass");
        var admMvc = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(adminLogin)))
                .andExpect(status().isOk()).andReturn();
        String admToken = mapper.readTree(admMvc.getResponse().getContentAsString()).get("token").asText();

        // Create expensive trip as admin
        var newTrip = Map.of("origin","TestCity","destination","RichCity","departureTime",java.time.LocalDateTime.now().plusDays(5).toString(),"price", new BigDecimal(\"1000.00\"));
        var createMvc = mvc.perform(post("/trips").header("Authorization","Bearer "+admToken)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newTrip)))
                .andExpect(status().isCreated()).andReturn();
        JsonNode created = mapper.readTree(createMvc.getResponse().getContentAsString());
        Integer expensiveTripId = created.get("id").intValue();

        // Try to buy expensive trip with low-balance user -> should return 422
        mvc.perform(post("/trips/"+expensiveTripId+"/buy").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnprocessableEntity());

        // Ensure non-admin cannot create trip
        mvc.perform(post("/trips").header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newTrip)))
                .andExpect(status().isForbidden());

        // GET /users/{id} returns public info
        var userInfoMvc = mvc.perform(get("/users/"+userId)).andExpect(status().isOk()).andReturn();
        JsonNode userInfo = mapper.readTree(userInfoMvc.getResponse().getContentAsString());
        assertThat(userInfo.get("id").intValue()).isEqualTo(userId);
        assertThat(userInfo.get("email").asText()).isEqualTo("test1@example.com");
    }
}
