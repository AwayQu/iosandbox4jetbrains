package plugin;

import bean.BeanFactory;
import bean.DevicePlist;
import bean.Sandbox;
import com.intellij.ui.mac.foundation.Foundation;
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


    public static ArrayList<DevicePlist> getDeviceInfoPlists() {
        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        ArrayList<DevicePlist> plists = new ArrayList<>();
        for (File f : files) {
            if (!f.isDirectory()) {
                continue;
            }
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

    /**
     * getAllItems
     *
     * @return
     */
    public static ArrayList<Sandbox> getSandboxList() {
        ArrayList<DevicePlist> devicePlists = ItemsData.getDeviceInfoPlists();

        ArrayList<Sandbox> sandboxes = new ArrayList<>();
        for (DevicePlist dp : devicePlists) {
            Sandbox sandbox = new Sandbox();

            sandbox.setUDID(dp.getUDID());

            sandbox.setBoxName(dp.getBoxName());
            sandbox.setVersion(dp.getVersion());
            sandbox.setDevice(dp.getDevice());

            sandbox.items = ItemsData.getProjects(sandbox);

            sandboxes.add(sandbox);

        }

        return sandboxes;
    }

    public static ArrayList<String> getProjects(Sandbox sandbox) {
        ArrayList arrayList = new ArrayList<>();

        String applicationPath = ItemsData.getDevicePath(sandbox.getUDID());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> projectSandboxPaths = new ArrayList<>();

        File applicationPathDir = new File(applicationPath);


        File[] files = applicationPathDir.listFiles();
        if (null == files) {
            return null;
        }

        try {


        for (File f : files) {
            String fileName = f.getAbsolutePath();
            String fileUrl = ItemsData.getDataDictPath(fileName);

            File fileUrlFile = new File(fileUrl);
            if (!fileUrlFile.exists()) {
                File fileNameFile = new File(fileName);
                File[] fileNameFiles = fileNameFile.listFiles();
                if (null == fileNameFiles) {
                    continue;
                }
                for (File f1 : fileNameFiles) {
                    if (f1.getName().contains(".app")) {
                        names.add(f1.getName().replace(".app", "").replace("-", "_"));
                        projectSandboxPaths.add(fileName);
                    }
                }
            } else {

                Foundation.NSDictionary dict = new Foundation.NSDictionary(Foundation.safeInvoke("NSDictionary", "dictionaryWithContentsOfFile:",
                        new Object[]{Foundation.nsString(fileUrl)}));
//                Map map = Foundation.NSDictionary.toStringMap(Foundation.safeInvoke("NSDictionary", "dictionaryWithContentsOfFile:",
//                        new Object[]{Foundation.nsString(fileUrl)}));

                String identifier = Foundation.toStringViaUTF8(dict.get("MCMMetadataIdentifier"));

                names.add(ItemsData.getAppName(identifier));
                projectSandboxPaths.add(fileName);
            }

        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sandbox.projectSandBoxPath = projectSandboxPaths;

        return names;
    }


    /**
     * applicationData path
     *
     * @param UDID
     * @return
     */
    public static String getDevicePath(String UDID) {

        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        String applicationPath = null;

        for (File f : files) {
            if (!f.isDirectory()) {
                continue;
            }
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


    public static String getDataDictPath(String filePath) {
        return filePath + "/.com.apple.mobile_container_manager.metadata.plist";
    }

    /**
     * applicationsDataPath
     *
     * @param path
     * @return
     */
    public static String getApplicationDataPath(String path) {
        return path + "/data/Containers/Data/Application";

    }

    public static String getAppName(String identifierName) {
        String[] arr = identifierName.split("\\.");
        String projectName = arr[arr.length - 1];
        projectName = projectName.replace("-", "_");
        return projectName;
    }

    public static File getSimulatorHomeFile() {
        File file = new File(ItemsData.getUserHome() + "/Library/Developer/CoreSimulator/Devices/");
        return file;
    }

    public static String getUserHome() {

        return System.getProperty("user.home");
    }


}
