package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Account;
import eu.siacs.signalghost.xmpp.stanzas.PresencePacket;

public interface OnPresencePacketReceived extends PacketReceived {
	public void onPresencePacketReceived(Account account, PresencePacket packet);
}
