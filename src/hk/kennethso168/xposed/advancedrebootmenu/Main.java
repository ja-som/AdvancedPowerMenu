package hk.kennethso168.xposed.advancedrebootmenu;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit{
	
	public static final String PACKAGE_NAME = Main.class.getPackage().getName();
    public static String MODULE_PATH = null;
    
    private static XSharedPreferences pref;
    
    //should set to false for release. Exceptions should be always logged
    public static final boolean WRITE_LOGS = false;
    
    public static final boolean writeLogs(){
    	pref.reload();
    	return pref.getBoolean("pref_verbose_log", true);
    }
    
    private static final String APP_NAME = "Advanced Reboot Menu";
    
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
    	pref = new XSharedPreferences(PACKAGE_NAME);
    }
    
    private static void log(String message) {
    	if(WRITE_LOGS) XposedBridge.log(APP_NAME + ": " + message);
    }
    
    @Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(ModRebootMenu.PACKAGE_NAME)){
        	ModRebootMenu.init(pref, lpparam.classLoader);
        }
    }
}
