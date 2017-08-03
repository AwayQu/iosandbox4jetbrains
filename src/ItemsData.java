import clojure.main;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by away on 03/08/2017.
 */

public class ItemsData {

    public ItemsData() {

    }

    static ArrayList getDeviceInfoPlists() {
        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        ArrayList plists = new ArrayList<>();
        for (File f: files) {

            try {
                File[] devicesPlists = f.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.equals("device.plist");
                    }
                });
                File devicesPlist = devicesPlists[0];
                plists.add(devicesPlist);
            } catch (NullPointerException e) {

            }
        }

        return plists;
    }

    static File getSimulatorHomeFile() {
        File file = new File(ItemsData.getUserHome() + "/Library/Developer/CoreSimulator/Devices/");
        return file;
    }

    static String getUserHome() {
        return "/Users/away";
    }


}
