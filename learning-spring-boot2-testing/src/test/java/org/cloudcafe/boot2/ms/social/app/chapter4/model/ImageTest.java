package org.cloudcafe.boot2.ms.social.app.chapter4.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageTest {


    @Test
    public void imageTest() {
        Image image = new Image("testId", "test.jpg");
        assertThat(image.getId()).isEqualTo("testId");
        assertThat(image.getName()).isEqualTo("test.jpg");
    }
}
