package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Account;

public interface OnStatusChanged {
	public void onStatusChanged(Account account);
}
