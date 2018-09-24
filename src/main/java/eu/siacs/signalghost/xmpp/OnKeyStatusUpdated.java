package eu.siacs.signalghost.xmpp;

import eu.siacs.signalghost.crypto.axolotl.AxolotlService;

public interface OnKeyStatusUpdated {
	public void onKeyStatusUpdated(AxolotlService.FetchStatus report);
}
