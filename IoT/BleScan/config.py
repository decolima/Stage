import json

class Config:
    @staticmethod
    def pathDB():
        try:
            with open('config.json') as f:
                config = json.load(f)
            dbPath = str(config['database']['path'])
            return dbPath
        except Exception as e:
            error_msg = f"Error in pathDB of Config: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")
    
    @staticmethod
    def nameDB():
        try:
            with open('config.json') as f:
                config = json.load(f)
            dbName = str(config['database']['name'])
            return dbName
        except Exception as e:
            error_msg = f"Error in nameDB of Config: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")

    @staticmethod
    def timeCycle():
        try:
            with open('config.json') as f:
                config = json.load(f)
            timecycle = str(config['cycle']['time'])
            return timecycle
        except Exception as e:
            error_msg = f"Error in timeCycle of Config: {str(e)}"
            print(error_msg)
            with open("error_log.txt", "a") as LogError:
                LogError.write(error_msg + "\n")