/*
 * Copyright (C) 2011 Jamie McDonald
 * 
 * This file is part of MusicBrainz for Android.
 * 
 * MusicBrainz for Android is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * MusicBrainz for Android is distributed in the hope that it 
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MusicBrainz for Android. If not, see 
 * <http://www.gnu.org/licenses/>.
 */

package org.musicbrainz.mobile.task;

import java.io.IOException;

import org.musicbrainz.android.api.data.Release;
import org.musicbrainz.android.api.data.UserData;
import org.musicbrainz.android.api.util.Credentials;
import org.musicbrainz.android.api.webservice.BarcodeNotFoundException;
import org.musicbrainz.android.api.webservice.MBEntity;
import org.musicbrainz.android.api.webservice.WebClient;
import org.musicbrainz.mobile.activity.base.DataQueryActivity;

public class LookupBarcodeTask extends MusicBrainzTask {

    private Credentials creds;
    private Release release;
    private UserData userData;
    private boolean barcodeExists = true;
    
    public LookupBarcodeTask(DataQueryActivity activity) {
        super(activity);
        if (activity.isUserLoggedIn()) {
            creds = activity.getCredentials();
        }
    }
    
    @Override
    protected Void run(String... barcode) throws Exception {
        if (creds == null) {
            getRelease(barcode[0]);
        } else {
            getReleaseWithUserData(barcode[0]);
        }
        return null;
    }
    
    private void getRelease(String barcode) throws IOException {
        WebClient client = new WebClient(userAgent);
        doBarcodeLookup(client, barcode);
        release = client.lookupReleaseFromBarcode(barcode);
    }

    private void getReleaseWithUserData(String barcode) throws IOException {
        WebClient client = new WebClient(creds);
        doBarcodeLookup(client, barcode);
        if (barcodeExists) {
            userData = client.getUserData(MBEntity.RELEASE_GROUP, release.getReleaseGroupMbid());
        }
    }
    
    private void doBarcodeLookup(WebClient client, String barcode) throws IOException {
        try {
            release = client.lookupReleaseFromBarcode(barcode);
        } catch (BarcodeNotFoundException e) {
            barcodeExists = false;
        }
    }
    
    public boolean doesBarcodeExist() {
        return barcodeExists;
    }
    
    public Release getRelease() {
        return release;
    }
    
    public UserData getUserData() {
        return userData;
    }
    
}
