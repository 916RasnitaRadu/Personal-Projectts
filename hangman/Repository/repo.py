class Repository:
    def __init__(self, file_path):
        self._data = list()
        self._file_path = file_path
        self._load_file()

    def save(self, sentence):
        self._data.append(sentence)
        self._save_file()

    def get_all(self):
        return self._data

    def _load_file(self):
        f = open(self._file_path, "rt")
        for line in f.readlines():
            if len(line) > 1:
                self.save(line)

        f.close()

    def _save_file(self):
        f = open(self._file_path, "wt")
        for line in self._data:
            f.write(line + "\n")

        f.close()

    def __len__(self):
        return len(self._data)

    def clear(self):
        self._data.clear()

    def find_sentence(self, entity):
        for sentence in self._data:
            if entity == sentence:
                return True
        return False
