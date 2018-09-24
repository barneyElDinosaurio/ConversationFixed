package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Account;
import eu.siacs.signalghost.xmpp.stanzas.MessagePacket;

public interface OnMessagePacketReceived extends PacketReceived {
	public void onMessagePacketReceived(Account account, MessagePacket packet);
}
