import argparse
import asyncio
from bleak import BleakScanner
from scandevicemanager import ScanDeviceManager

class ScanDevices:
    def run(self):
        try:
            parser = argparse.ArgumentParser()
            parser.add_argument(
                "--macos-use-bdaddr",
                action="store_false",
                help="when true use Bluetooth address instead of UUID on macOS",
            )
            args = parser.parse_args()

            asyncio.run(self.scan(args))
        except Exception as e:
            error_msg = f"Error in run of ScanDevices: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

    async def scan(self, args: argparse.Namespace):
        try:
            print("scanning for 5 seconds, please wait...")
            mac_list = []

            devices = await BleakScanner.discover(
                return_adv=True, cb=dict(use_bdaddr=args.macos_use_bdaddr)
            )

            for d, a in devices.values():
                mac_list.append(d)
                print()
                print(d)
                print("-" * len(str(d)))
                #print(a)
            
            ScanDeviceManager.save_scan_devices(mac_list)
        except Exception as e:
            error_msg = f"Error in scan of ScanDevices: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

if __name__ == "__main__":
    try:
        minha_classe = ScanDevices()
        minha_classe.run()
    except Exception as e:
        error_msg = f"Error in main of ScanDevices: {str(e)}"
        print(error_msg)
        with open("error_log.txt", "a") as LogError:
            LogError.write(error_msg + "\n")
