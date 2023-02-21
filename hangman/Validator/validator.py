class Validator:
    def __init__(self, repo):
        self._repo = repo

    def validate(self, entity):
        sentence = entity
        words = list(sentence.split())
        # at least one word
        if len(words) < 1:
            return False
        # every word has at least 3 letters
        if any([len(word) < 3 for word in words]):
            return False
        # there are no duplicate sentences
        if self._repo.find_sentence(entity):
            return False
        return True




