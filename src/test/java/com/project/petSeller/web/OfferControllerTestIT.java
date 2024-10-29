package com.project.petSeller.web;

import com.project.petSeller.model.entity.OfferEntity;
import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.testUtils.TestDataUtil;
import com.project.petSeller.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTestIT {

    private static final String TEST_USER1_EMAIL = "user1@example.com";
    private static final String TEST_USER2_EMAIL = "user2@example.com";
    private static final String TEST_ADMIN_EMAIL = "admin@example.com";

    @Autowired
    private TestDataUtil testDataUtil;

    @Autowired
    private UserTestDataUtil userTestDataUtil;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();
    }

    @AfterEach
    void tearDown() {

        testDataUtil.cleanUp();
        userTestDataUtil.cleanUp();

    }


    @Test
    void testAnonymousDeletionFails() throws Exception {
        UserEntity owner = userTestDataUtil.createTestUser("test@example.com");

        OfferEntity offerEntity = testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        delete("/offer/{uuid}", offerEntity.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));


    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL)
    void testNonAdminUserOwnedOffer() throws Exception {
        UserEntity owner = userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        OfferEntity offerEntity = testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        delete("/offer/{uuid}", offerEntity.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));

    }

    @WithMockUser(username = TEST_USER2_EMAIL)
    @Test
    void testNonAdminUserNotOwnedOffer() throws Exception {
        UserEntity owner = userTestDataUtil.createTestUser(TEST_USER1_EMAIL);
        userTestDataUtil.createTestUser(TEST_USER2_EMAIL);

        OfferEntity offerEntity = testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                delete("/offer/{uuid}", offerEntity.getUuid())
                        .with(csrf())
        ).andExpect(status().isForbidden());


    }

    @Test
    @WithMockUser(
            username = TEST_ADMIN_EMAIL,
    roles = {"USER", "ADMIN"})
    void testAdminUserNotOwnedOffer() throws Exception {

        UserEntity owner = userTestDataUtil.createTestUser(TEST_USER1_EMAIL);
        userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        OfferEntity offerEntity = testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                delete("/offer/{uuid}", offerEntity.getUuid())
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));

    }
}
