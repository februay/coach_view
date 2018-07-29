package indi.xp.common.utils.excel;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XlsxWorkbookToTablesHandler extends DefaultHandler {
    private Map<String, String> sheetNameToInternalId;

    public XlsxWorkbookToTablesHandler(Map<String, String> sheetNameToInternalId) {
        this.sheetNameToInternalId = sheetNameToInternalId;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("sheet".equals(qName)) {
            String state = attributes.getValue("state");
            if (state != null && state.equalsIgnoreCase("hidden")) {
                return;
            }
            String sheetName = attributes.getValue("name");
            String relationId = attributes.getValue("r:id");
            sheetNameToInternalId.put(sheetName, relationId);
        }

    }
}
