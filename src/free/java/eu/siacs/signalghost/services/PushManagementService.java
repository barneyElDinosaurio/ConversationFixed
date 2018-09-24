package eu.siacs.signalghost.services;

import eu.siacs.signalghost.entities.Account;

public class PushManagementService {

	protected final XmppConnectionService mXmppConnectionService;

	public PushManagementService(XmppConnectionService service) {
		this.mXmppConnectionService = service;
	}

	public void registerPushTokenOnServer(Account account) {
		//stub implementation. only affects playstore flavor
	}

	public boolean available(Account account) {
		return false;
	}

	public boolean isStub() {
		return true;
	}
}
