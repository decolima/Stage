import os
import sqlite3
from config import Config

class SQLiteClient:
    
    def __init__(self):
        try:
            self.database_path = os.path.join(Config.pathDB(), Config.nameDB())
            self.conn = None
            self.cursor = None
            self.ensure_database_permissions()
        except Exception as e:
            error_msg = f"Error in __init__ of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
            
    def ensure_database_permissions(self):
        try:
            os.chmod(self.database_path, 0o666)

        except Exception as e:
            error_msg = f"Error in ensure_database_permissions of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
                

    def connect(self):
        try:
            self.conn = sqlite3.connect(self.database_path)
            self.cursor = self.conn.cursor()
        except Exception as e:
            error_msg = f"Error in connect of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

    def execute_query(self, query, params=None):
        try:
            if not self.conn:
                self.connect()
            if params:
                self.cursor.execute(query, params)
            else:
                self.cursor.execute(query)
            rows = self.cursor.fetchall()
            return rows
        except Exception as e:
            error_msg = f"Error in execute_query of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

    def execute_command(self, command, params=None):
        try:
            if not self.conn:
                self.connect()
            if params:
                self.cursor.execute(command, params)
            else:
                self.cursor.execute(command)
            self.conn.commit()
        except Exception as e:
            error_msg = f"Error in execute_command of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
        
    def create_table(self, table_name, columns):
        try:
            if not self.conn:
                self.connect()
            self.cursor.execute(f"CREATE TABLE IF NOT EXISTS {table_name} ({columns})")
            self.conn.commit()
        except Exception as e:
            error_msg = f"Error in create_table of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

    def close(self):
        try:
            if self.conn:
                self.conn.close()
                self.conn = None
                self.cursor = None
        except Exception as e:
            error_msg = f"Error in close of SQLiteClient: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
