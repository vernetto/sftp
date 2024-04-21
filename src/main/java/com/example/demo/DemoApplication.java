package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void startServer() throws IOException {

		SshServer server = SshServer.setUpDefaultServer();
		server.setPort(22);
		server.setPasswordAuthenticator((username, password, session) -> true); // For demo purposes
		server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser")));
		server.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
		server.start();
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			startServer();
		}
		catch (Exception e) {
			log.error("error", e);
		}


	}
}
