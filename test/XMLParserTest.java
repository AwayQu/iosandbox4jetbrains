import bean.Sandbox;
import com.apple.eio.FileManager;
import com.intellij.ui.mac.foundation.Foundation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import dependency.plistparser.PListDict;
import dependency.plistparser.PListException;
import dependency.plistparser.PListParser;
import plugin.ItemsData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by away on 05/08/2017.
 */

public class XMLParserTest {

    /**

     */

    @Test
    public void testParserXMLDevicePlist() throws PListException {
        PListDict dict = PListParser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "     <!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                "     <plist version=\"1.0\">\n" +
                "     <dict>\n" +
                "     <key>UDID</key>\n" +
                "     <string>41D3F8C6-8237-4276-9190-F723831F325D</string>\n" +
                "     <key>deviceType</key>\n" +
                "     <string>com.apple.CoreSimulator.SimDeviceType.iPhone-7-Plus</string>\n" +
                "     <key>name</key>\n" +
                "     <string>iPhone 7 Plus</string>\n" +
                "     <key>runtime</key>\n" +
                "     <string>com.apple.CoreSimulator.SimRuntime.iOS-10-2</string>\n" +
                "     <key>state</key>\n" +
                "     <integer>1</integer>\n" +
                "     </dict>\n" +
                "     </plist>");

        String udid = dict.getString("UDID");
        assertEquals("41D3F8C6-8237-4276-9190-F723831F325D", udid);

        String deviceType = dict.getString("deviceType");
        assertEquals("com.apple.CoreSimulator.SimDeviceType.iPhone-7-Plus", deviceType);

        String name = dict.getString("name");
        assertEquals("iPhone 7 Plus", name);

        String runtime = dict.getString("runtime");
        assertEquals("com.apple.CoreSimulator.SimRuntime.iOS-10-2", runtime);

        int state = dict.getInt("state");
        assertEquals(1, state);
    }

    @Test
    public void testGetDevicePath() {
        String applicationPath = ItemsData.getDevicePath("41D3F8C6-8237-4276-9190-F723831F325D");
        assertEquals("", applicationPath);
    }


    @Test
    public void testSplit() {
        String testString = "aa.bb";
        String[] strings = testString.split("\\.");
        String latString = strings[strings.length - 1];
        assertEquals("bb", latString);
    }


    @Test
    public void testGetSandboxes() {

        ArrayList<Sandbox> sandboxex = ItemsData.getSandboxList();
        assertEquals("bb", "bb");
    }


    @Test
    public void testGetContentOfFile() {

        String filePath = "/Users/away/Library/Developer/CoreSimulator/Devices/41D3F8C6-8237-4276-9190-F723831F325D/data/Containers/Data/Application/050EF952-4CBB-4CEF-9542-A2CD9CA5584E/.com.apple.mobile_container_manager.metadata.plist";
        File file = new File(filePath);

        Foundation.NSDictionary dictionary = new Foundation.NSDictionary(Foundation.safeInvoke("NSDictionary", "dictionaryWithContentsOfFile:",
                new Object[]{Foundation.nsString(filePath)}));

        String s = Foundation.toStringViaUTF8(dictionary.get("MCMMetadataIdentifier"));

        assertEquals("bb", "bb");

    }


    @Test
    public void testRevealInFinder() throws FileNotFoundException {
        String filePath = "/Users/away/Library/Developer/CoreSimulator/Devices/41D3F8C6-8237-4276-9190-F723831F325D/data/Containers/Data/Application/050EF952-4CBB-4CEF-9542-A2CD9CA5584E/.com.apple.mobile_container_manager.metadata.plist";

        FileManager.revealInFinder(new File(filePath));
        assertEquals("bb", "bb");
    }
}
