package eu.siacs.signalghost.xmpp.jingle;

import eu.siacs.signalghost.entities.Account;
import eu.siacs.signalghost.xmpp.PacketReceived;
import eu.siacs.signalghost.xmpp.jingle.stanzas.JinglePacket;

public interface OnJinglePacketReceived extends PacketReceived {
	void onJinglePacketReceived(Account account, JinglePacket packet);
}
