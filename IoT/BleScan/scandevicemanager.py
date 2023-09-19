import sqlite3
from datetime import datetime
from sqllite import SQLiteClient

class ScanDeviceManager:
    
    @staticmethod
    def setupdb():
        try:
            sqlite_client = SQLiteClient()
            comando_sql = '''
                CREATE TABLE IF NOT EXISTS taglog (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    address TEXT,
                    name TEXT,
                    datadiscovery DATETIME,
                    status INTEGER,
                    idpublish INTEGER
                )
            '''
            filds = '''
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    address TEXT,
                    name TEXT,
                    datadiscovery DATETIME,
                    status INTEGER,
                    idpublish INTEGER
            '''
            sqlite_client.create_table("taglog", filds)
            sqlite_client.close()
        except Exception as e:
            error_msg = f"Error in setupdb: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
    
    @staticmethod
    def save_scan_devices(mac_addresses):
        try:
            sqlite_client = SQLiteClient()
            
            for mac_address in mac_addresses:
                scan_date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                status = 0
                idpublish = 0
                command = "INSERT INTO taglog (address, name, datadiscovery, status, idpublish) VALUES (?, ?, ?, ?, ?)"
                params = (mac_address.address, mac_address.name, scan_date, status, idpublish)
                sqlite_client.execute_command(command, params)
            
            sqlite_client.close()
        except Exception as e:
            error_msg = f"Error in save_scan_devices: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

