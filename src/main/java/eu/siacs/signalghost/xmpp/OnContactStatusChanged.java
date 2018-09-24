package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Contact;

public interface OnContactStatusChanged {
	public void onContactStatusChanged(final Contact contact, final boolean online);
}
