/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tag.tagmanager;

import java.io.IOException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;


/**
 *
 * @author andrelima
 */
public class manager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        // TODO code application logic here
        System.out.println("tag.tagmanager.manager.main()");
        
        BluetoothDeviceScanner deviceScanner = new BluetoothDeviceScanner();
        deviceScanner.scanDevices();
        
/*        LocalDevice device = LocalDevice.getLocalDevice();
        
        RemoteDevice[] remotedevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
        
        for (RemoteDevice rd : remotedevice) {
            
            System.out.println("DeviceName: " + rd.getFriendlyName(false));
            System.out.println("Address: " + rd.getBluetoothAddress());
            
        }
        */
        
        
    }
    
}
