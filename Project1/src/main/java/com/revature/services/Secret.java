package com.revature.services;

import java.util.Arrays;

import com.textmagic.sdk.RestClient;
import com.textmagic.sdk.RestException;
import com.textmagic.sdk.resource.instance.TMNewMessage;
		 
public class Secret {
	RestClient rc;
	TMNewMessage tmnm;

	Secret() {
		rc = new RestClient("jordangibsonprice", "my key");
		tmnm = rc.getResource(TMNewMessage.class);
	}

	public void setMessage(String subject) {
		tmnm.setText(subject);
	}

	public void setMyPhone() {
		tmnm.setPhones(Arrays.asList(new String[] { "my number" }));
	}

	public void sendMessage() {
		try {
			tmnm.send();
		} catch (final RestException e) {
			System.out.println(e.getErrors());
			throw new RuntimeException(e);
		}
		System.out.println(tmnm.getId());
	}
}	


