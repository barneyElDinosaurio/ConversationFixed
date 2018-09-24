package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Account;

public interface OnMessageAcknowledged {
	public void onMessageAcknowledged(Account account, String id);
}
