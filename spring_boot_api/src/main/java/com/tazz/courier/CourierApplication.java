package com.tazz.courier;

import java.io.IOException;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourierApplication {

	public static void main(String[] args) throws IOException {

		CourierApplication instance = new CourierApplication();
		InputStream serviceAccount = instance.getFileAsIOStream("key.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://tazz-97add-default-rtdb.europe-west1.firebasedatabase.app")
				.build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(CourierApplication.class, args);
	}

	private InputStream getFileAsIOStream(final String fileName) {
		InputStream ioStream = this.getClass()
				.getClassLoader()
				.getResourceAsStream(fileName);

		if (ioStream == null) {
			throw new IllegalArgumentException(fileName + " is not found");
		}
		return ioStream;
	}
}
