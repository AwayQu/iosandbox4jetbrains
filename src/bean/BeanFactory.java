/**
 * Created by away on 05/08/2017.
 */
package bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import dependency.plistparser.PListDict;
import dependency.plistparser.PListException;
import dependency.plistparser.PListParser;

public class BeanFactory {

    public static DevicePlist createDevicePlistBean(String filePath) throws FileNotFoundException, PListException {
        InputStream is = new FileInputStream(filePath);
        PListDict dict = PListParser.parse(is);

        String udid = dict.getString("UDID");
        String deviceType = dict.getString("deviceType");

        String name = dict.getString("name");
        String runtime = dict.getString("runtime");
        int state = dict.getInt("state");

        DevicePlist devicePlist = new DevicePlist();
        devicePlist.setUDID(udid);
        devicePlist.setDeviceType(deviceType);
        devicePlist.setName(name);
        devicePlist.setRuntime(runtime);
        devicePlist.setState(state);

        return devicePlist;
    }


}
