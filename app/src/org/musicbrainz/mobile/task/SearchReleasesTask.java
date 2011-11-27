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

import java.util.LinkedList;

import org.musicbrainz.android.api.data.ReleaseStub;
import org.musicbrainz.android.api.webservice.WebClient;
import org.musicbrainz.mobile.activity.base.DataQueryActivity;

public class SearchReleasesTask extends MusicBrainzTask {

    private LinkedList<ReleaseStub> results;
    
    public SearchReleasesTask(DataQueryActivity activity) {
        super(activity);
    }
    
    @Override
    protected Void run(String... term) throws Exception {
        WebClient client = new WebClient(userAgent);
        results = client.searchRelease(term[0]);
        return null;
    }
    
    public LinkedList<ReleaseStub> getResults() {
        return results;
    }
    
}
