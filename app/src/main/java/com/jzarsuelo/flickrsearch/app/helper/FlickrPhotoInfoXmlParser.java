package com.jzarsuelo.flickrsearch.app.helper;

import android.util.Log;
import android.util.Xml;

import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoInfoModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JanPaolo on 5/15/2016.
 */
public class FlickrPhotoInfoXmlParser {

    private static final String TAG = FlickrXmlParser.class.getSimpleName();

    private static final String ELEMENT_PHOTO = "photo";

    private static final String ns = null;

    public FlickrPhotoInfoModel parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag(); // rsp
            parser.nextTag(); // photo
            return readPhotoInfo(parser);
        } finally {
            in.close();
        }
    }

    private FlickrPhotoInfoModel readPhotoInfo(XmlPullParser parser) throws IOException, XmlPullParserException {

        Log.d(TAG, "yyy " + parser.getName());

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag

            Log.d(TAG, name);
        }
        return null;
    }

}
