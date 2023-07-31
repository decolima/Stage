#classe utilizzata per realizare il salvataggio delle informazione dello scanner nel database
import sqlite3
from datetime import datetime
from sqllite import SQLiteClient

class ScanDeviceManager:
    
    @staticmethod
    def setupdb ():
        sqlite_client = SQLiteClient()
        comando_sql = '''
            CREATE TABLE IF NOT EXISTS taglog (
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
        #,PRIMARY KEY (address, datadiscovery)
        #self.sqlite_client.execute_command(comando_sql)
        sqlite_client.create_table("taglog", filds)
        
        
    @staticmethod
    def save_scan_devices(mac_addresses):
        
        sqlite_client = SQLiteClient()
        
        for mac_address in mac_addresses:
            scan_date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            status = 0
            idpublish = 0
            command = "INSERT INTO taglog (address, name, datadiscovery, status, idpublish) VALUES (?, ?, ?, ?, ?)"
            #print(mac_address.address)
            params = (mac_address.address, mac_address.name, scan_date, status, idpublish)
            sqlite_client.execute_command(command, params)
            
        #query = "SELECT * FROM taglog"
        #results = sqlite_client.execute_query(query)
        #for row in results:
        #   print(row)
        
        sqlite_client.close()
    

    