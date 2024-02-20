package com.gacuna.scifistarship;

import com.gacuna.scifistarship.controller.StarShipController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class ScifistarshipApplicationTests {

    @Autowired
    private StarShipController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
