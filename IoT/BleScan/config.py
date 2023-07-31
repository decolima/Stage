import json

class Config:
    @staticmethod
    def pathDB():
        # Get Db Path
        with open('config.json') as f:
            config = json.load(f)
              
        dbPath = str(config['database']['path'])
        return dbPath
    
    @staticmethod
    def nameDB():
        with open('config.json') as f:
            config = json.load(f)
              
        dbName = str(config['database']['name'])
        return dbName

