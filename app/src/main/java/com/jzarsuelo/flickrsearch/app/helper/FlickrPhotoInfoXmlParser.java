package com.jzarsuelo.flickrsearch.app.helper;

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

    private static final String ELEMENT_TITLE = "title";
    private static final String ELEMENT_OWNER = "owner";
    private static final String ELEMENT_DATES = "dates";

    private static final String ATTR_USERNAME = "username";
    private static final String ATTR_POSTED = "posted";


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

        String title = null;
        String owner = null;
        String datePosted = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(ELEMENT_OWNER)) {
                owner = readOwner(parser);
            } else if (name.equals(ELEMENT_TITLE)) {
                title = readTitle(parser);
            } else if (name.equals(ELEMENT_DATES)){
                datePosted = readDatePosted(parser);
            } else {
                skip(parser);
            }

        }
        return new FlickrPhotoInfoModel(datePosted, owner, title);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readDatePosted(XmlPullParser parser) throws IOException, XmlPullParserException {
        String datePosted = "";

        parser.require(XmlPullParser.START_TAG, ns, ELEMENT_DATES);
        String tag = parser.getName();
        String posted = parser.getAttributeValue(ns, ATTR_POSTED);

        if (tag.equals(ELEMENT_DATES)) {
            datePosted = posted;
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, ns, ELEMENT_DATES);


        return datePosted;
    }

    private String readOwner(XmlPullParser parser) throws IOException, XmlPullParserException {
        String owner = "";

        parser.require(XmlPullParser.START_TAG, ns, ELEMENT_OWNER);
        String tag = parser.getName();
        String user = parser.getAttributeValue(ns, ATTR_USERNAME);

        if (tag.equals(ELEMENT_OWNER)) {
            owner = user;
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, ns, ELEMENT_OWNER);

        return owner;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ELEMENT_TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, ELEMENT_TITLE);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
