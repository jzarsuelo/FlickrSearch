package com.jzarsuelo.flickrsearch.app.helper;

import android.util.Xml;

import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JanPaolo on 5/14/2016.
 *
 * Helper class to parse search result from Flickr xml response
 */
public class FlickrSearchResultXmlParser {

    public static final String TAG = FlickrSearchResultXmlParser.class.getSimpleName();

    private static final String ns = null;

    private static final String ATTR_ID = "id";
    private static final String ATTR_OWNER = "owner";
    private static final String ATTR_SECRET = "secret";
    private static final String ATTR_SERVER = "server";
    private static final String ATTR_FARM = "farm";
    private static final String ATTR_TITLE = "title";

    private static final String ELEMENT_PHOTO = "photo";

    /**
     * Start parsing the XML data
     * @param in
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public List<FlickrPhotoModel> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag(); // skip rsp
            parser.nextTag(); // skip photos

            return readPhotos(parser);
        } finally {
            in.close();
        }
    }

    /**
     * Read photo from XML data
     * @param parser
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List<FlickrPhotoModel> readPhotos(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<FlickrPhotoModel> flickrPhotoModelList = new ArrayList<FlickrPhotoModel>();

        // required element
        parser.require(XmlPullParser.START_TAG, ns, "photos");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            // Starts by looking for the entry tag
            if ( ELEMENT_PHOTO.equals( name )) {
                parser.next();

                FlickrPhotoModel model = readFlickrPhotoModel(parser);
                flickrPhotoModelList.add( model );
            }
        }
        return flickrPhotoModelList;
    }

    /**
     * Extract the attribute values to instantiate {@link FlickrPhotoModel}
     *
     * @param parser
     * @return Instance of {@link FlickrPhotoModel}
     * @throws XmlPullParserException
     * @throws IOException
     */
    private FlickrPhotoModel readFlickrPhotoModel(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        return new FlickrPhotoModel(
                parser.getAttributeValue(ns, ATTR_ID),
                parser.getAttributeValue(ns, ATTR_OWNER),
                parser.getAttributeValue(ns, ATTR_SECRET),
                parser.getAttributeValue(ns, ATTR_SERVER),
                parser.getAttributeValue(ns, ATTR_FARM),
                parser.getAttributeValue(ns, ATTR_TITLE)
        );
    }

}
