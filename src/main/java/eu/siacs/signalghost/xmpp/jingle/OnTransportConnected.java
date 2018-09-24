package eu.siacs.signalghost.xmpp.jingle;

public interface OnTransportConnected {
	public void failed();

	public void established();
}
