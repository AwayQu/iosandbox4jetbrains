import bean.BeanFactory;
import bean.DevicePlist;
import bean.Sandbox;
import dependency.plistparser.PListException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by away on 03/08/2017.
 */

public class ItemsData {

    public ItemsData() {

    }



    static ArrayList<DevicePlist> getDeviceInfoPlists() {
        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        ArrayList<DevicePlist> plists = new ArrayList<>();
        for (File f: files) {

            try {
                String devicePlistPath = f.getAbsolutePath() + "/device.plist";
                DevicePlist devicePlist = BeanFactory.createDevicePlistBean(devicePlistPath);
                plists.add(devicePlist);
            } catch (NullPointerException e) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (PListException e) {
                e.printStackTrace();
            }

        }

        return plists;
    }

    static ArrayList<Sandbox> getSandboxList() {
        ArrayList<DevicePlist> devicePlists = ItemsData.getDeviceInfoPlists();

        ArrayList<Sandbox> sandboxes = new ArrayList<>();
        for (DevicePlist dp : devicePlists) {
            Sandbox sandbox = new Sandbox();

            sandbox.setUDID(dp.getUDID());

            sandbox.setBoxName(dp.getBoxName());
            sandbox.setVersion(dp.getVersion());
            sandbox.setDevice(dp.getDevice());

            // todo: get projects sandboxes

            sandboxes.add(sandbox);

        }

        return null;
    }

    static ArrayList getProjects(Sandbox sandbox) {
        ArrayList arrayList = new ArrayList<>();



        return null;
    }


    /**
     * applicationData path
     * @param UDID
     * @return
     */
    static String getDevicePath(String UDID) {

        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        String applicationPath = null;

        for (File f: files) {
            String devicePlistPath = f.getAbsolutePath() + "/device.plist";
            String fPath = f.getAbsolutePath();
            try {
                DevicePlist devicePlist = BeanFactory.createDevicePlistBean(devicePlistPath);
                if (devicePlist.getUDID().contains(UDID)) {
                    applicationPath = ItemsData.getApplicationDataPath(fPath);
                    File applicationFile = new File(applicationPath);
                    if (!applicationFile.exists()) {
                        return null;
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (PListException e) {
                e.printStackTrace();
            }
        }

        return applicationPath;

    }


    /**
     * applicationsDataPath
     * @param path
     * @return
     */
    static String getApplicationDataPath(String path) {
        return path + "/data/Containers/Data/Application";

    }

    static File getSimulatorHomeFile() {
        File file = new File(ItemsData.getUserHome() + "/Library/Developer/CoreSimulator/Devices/");
        return file;
    }

    static String getUserHome() {
        return "/Users/away";
    }


}
