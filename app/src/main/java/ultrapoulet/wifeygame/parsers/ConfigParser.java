package ultrapoulet.wifeygame.parsers;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 11/26/2017.
 */

public class ConfigParser extends DefaultHandler {

    List<String> files = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("include")) {
            String file = "config/" + attributes.getValue("file");
            files.add(file);
        } else if (qName.equalsIgnoreCase("config")) {
            //Do nothing, but valid
        } else {
            Log.e("ConfigParser", "Invalid qName: " + qName);
        }
    }

    public List<String> getConfigList() {
        return files;
    }
}
