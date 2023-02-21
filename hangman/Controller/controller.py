import random


class Controller:
    def __init__(self, repo):
        self._repo = repo
        self._hangman = "hangman"

    def save_to_repo(self, sentence):
        self._repo.save(sentence)

    def pick_random(self):
        sentences = self._repo.get_all()
        picked_sentence = random.choice(sentences)
        return picked_sentence

    @staticmethod
    def is_over1(sent1, sent2):
        for i in range(len(sent1)):
            if sent2[i] != sent1[i]:
                return False
        return True

    def is_over2(self, sent):
        if sent == self._hangman:
            return True
        return False

    @staticmethod
    def get_set_of_known_letter(words):
        letters_known = set()
        for word in words:
            first_letter = word[0]
            last_letter = word[len(word) - 1]
            letters_known.add(first_letter)
            letters_known.add(last_letter)
        return letters_known

    def make_sentence(self, unknown_sentence):
        sent = unknown_sentence
        words = sent.split()
        new_sentence = ""
        letters_known = self.get_set_of_known_letter(words)
        for word in words:
            for letter in word:
                if letter in letters_known:
                    new_sentence += letter
                else:
                    new_sentence += "_"
            new_sentence += " "

        new_sentence = new_sentence[:-1]
        return new_sentence

    def make_hangman(self):
        return "_______"

    def update_hangman(self, n, hangman):
        updated_hangman = ""
        for i in range(0, n+1):
            updated_hangman += self._hangman[i]
        for i in range(n+1, len(hangman)):
            updated_hangman += "_"
        return updated_hangman


    @staticmethod
    def update_sentence(letter, sentence_built, unknown_sentence):
        updated_sentence = ""
        for i in range(len(unknown_sentence)):
            if unknown_sentence[i] == letter:
                updated_sentence += letter
            else:
                updated_sentence += sentence_built[i]
        return updated_sentence

