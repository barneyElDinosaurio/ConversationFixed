package eu.siacs.signalghost.xmpp.stanzas;

public class PresencePacket extends AbstractAcknowledgeableStanza {

	public PresencePacket() {
		super("presence");
	}
}
