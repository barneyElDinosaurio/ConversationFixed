package eu.siacs.signalghost.xmpp.stanzas.csi;

import eu.siacs.signalghost.xmpp.stanzas.AbstractStanza;

public class InactivePacket extends AbstractStanza {
	public InactivePacket() {
		super("inactive");
		setAttribute("xmlns", "urn:xmpp:csi:0");
	}
}
