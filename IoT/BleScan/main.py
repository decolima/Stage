from datetime import datetime, timedelta
import time
from blescan import ScanDevices
from scandevicemanager import ScanDeviceManager
from config import Config

class Main:
    @staticmethod
    def main():
        try:
            ScanDeviceManager.setupdb()            
            while True:
                try:
                    scan = ScanDevices()
                    current_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                    print("Start at:", current_time)
                    scan.run()
                    current_time = datetime.now() + timedelta(minutes=int(Config.timeCycle()))
                    print("Finished! Next cycle at:", current_time.strftime("%Y-%m-%d %H:%M:%S"))
                    time.sleep(int(Config.timeCycle()) * 60)
                except Exception as e:
                    error_msg = f"Error at {datetime.now()}: {str(e)}"
                    print(error_msg)
                    with open("error_log.txt", "a") as LogError:
                        LogError.write(error_msg + "\n")
                    time.sleep(int(Config.timeCycle()) * 60)
        except KeyboardInterrupt:
            print("Application stopped by user.")
        except Exception as e:
            print("Critical error:", str(e))

# Create a instance of Main
my_main = Main()

# Start
my_main.main()
