package test;

import client.MoodleWsClient;
import moodleauth.Moodleauth;

public class TestAuthentication {
	public static void main(String[] args) {
		boolean result = MoodleWsClient.authenticate("anil", "anilanil");
		System.out.println("User anil Password anil : " + result);
		
	}
}
