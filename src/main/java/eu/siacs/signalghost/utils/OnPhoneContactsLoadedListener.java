package eu.siacs.signalghost.utils;

import android.os.Bundle;

import java.util.List;

public interface OnPhoneContactsLoadedListener {
	public void onPhoneContactsLoaded(List<Bundle> phoneContacts);
}
