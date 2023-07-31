from datetime import datetime, timedelta
import time
from blescan import ScanDevices
from scandevicemanager import ScanDeviceManager

class Main:
    def main(self):
        ScanDeviceManager.setupdb()
        scan = ScanDevices()
        while True:
            current_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            print("Start at :", current_time)
            scan.run()
            #current_time = current_time + timedelta(minutes=5)
            current_time = datetime.now() + timedelta(minutes=3)
            print("Finished! Next ciclo at :", current_time.strftime("%Y-%m-%d %H:%M:%S"))
            time.sleep(3*60)

# Criar uma instância da classe Main
my_main = Main()

# Chamar o método main
my_main.main()
