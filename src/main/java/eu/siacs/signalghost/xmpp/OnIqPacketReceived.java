package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.entities.Account;
import eu.siacs.signalghost.xmpp.stanzas.IqPacket;

public interface OnIqPacketReceived extends PacketReceived {
	public void onIqPacketReceived(Account account, IqPacket packet);
}
