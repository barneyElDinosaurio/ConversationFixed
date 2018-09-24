package eu.siacs.signalghost.xmpp.jingle;

import eu.siacs.signalghost.entities.DownloadableFile;

public interface OnFileTransmissionStatusChanged {
	void onFileTransmitted(DownloadableFile file);

	void onFileTransferAborted();
}
