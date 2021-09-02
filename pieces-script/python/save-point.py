import json
import os
import logging

class SavePoint:

    def __init__(self, save_point_file_name):
        self.dir = "save-point"
        self.save_point_file_name = save_point_file_name
        os.makedirs(self.dir, exist_ok=True)
        self.save_point_file = "{}/{}.json".format(self.dir, save_point_file_name)

    def recoverFromSavePoint(self):
        if not os.path.isfile(self.save_point_file):
            return

        with open(self.save_point_file, 'r') as f:
            key = json.load(f)

        return key

    def savePoint(self, key):
        logging.info('current: %s, key: %s', self.save_point_file_name, key)

        with open(self.save_point_file, 'w+') as f:
            json.dump(key, f)

    def clearSavePoint(self):
        if not os.path.isfile(self.save_point_file):
            return
        os.remove(self.save_point_file)
