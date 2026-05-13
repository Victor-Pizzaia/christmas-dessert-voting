package com.christmas.dessert.voting.christmas_dessert_voting;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModulithVerificationTest {

    @Test
    void verifyModulith() {
        var modules = ApplicationModules.of(ChristmasDessertVotingApplication.class);
        modules.verify();
    }
}
