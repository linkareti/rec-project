package com.linkare.rec.impl.newface.component;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class ChatTest {

    @Test
    public void testChatTemplateHtmlResource() throws Exception {
	InputStream resourceAsStream = Chat.class.getResourceAsStream("resources/chatTemplate.htm");
	assertNotNull("resource not found", resourceAsStream);
    }

}
