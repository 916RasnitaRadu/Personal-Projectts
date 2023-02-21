import random


class Controller:
    def __init__(self, repo):
        self._repo = repo

    def pick_random(self):
        lista_prop = self._repo.get_all()
        sentence = random.choice(lista_prop)
        return sentence

    @staticmethod
    def scramble_string(word):
        first_letter = word[0]
        last_letter = word[len(word) - 1]
        new_word = word[1:-1]

        new_word_list = list(new_word)
        random.shuffle(new_word_list)
        new_word = "".join(new_word_list)

        new_word += last_letter
        new_word2 = first_letter
        new_word2 += new_word

        return new_word2

    @staticmethod
    def split_command(cmd_line):
        cmd_line = cmd_line.strip()
        cmd_line = cmd_line.lower()
        tokens = cmd_line.split(maxsplit=1)
        cmd_word = tokens[0] if len(tokens) > 0 else None
        cmd_params = tokens[1] if len(tokens) == 2 else None
        return cmd_word, cmd_params

    @staticmethod
    def split_params(cmd_params):
        if cmd_params is None:
            return []
        else:
            params_list = cmd_params.split()
            return params_list

    @staticmethod
    def compute_score(unknwn_sentence):
        score = 0
        words = unknwn_sentence.split()
        for word in words:
            score += len(word)
        return score

    def get_scrambled_sentence(self):
        sentence = self.pick_random()
        sentence = sentence[:-1]
        scrambled_sentence = ""
        words = sentence.split()
        for word in words:
            if len(word) >= 4:
                new_word = self.scramble_string(word)
                scrambled_sentence += new_word
                scrambled_sentence += " "
            else:
                scrambled_sentence += word
                scrambled_sentence += " "
        scrambled_sentence = scrambled_sentence[:-1]
        return sentence, scrambled_sentence

    @staticmethod
    def swap_letters(word1, letter1, word2, letter2, unkwn_sentence):
        updt_sentence = ""
        """
        l = [ ..... ]
        word1 litera1 - word2 litera2 
        l[word1] == litera1
        """
        words = unkwn_sentence.split()
        if word1 == word2:
            primu_cuvant = list(words[word1])
            primu_cuvant[letter1], primu_cuvant[letter2] = primu_cuvant[letter2], primu_cuvant[letter1]

        else:
            primu_cuvant = list(words[word1])
            al_doilea_cuvant = list(words[word2])

            primu_cuvant[letter1], al_doilea_cuvant[letter2] = al_doilea_cuvant[letter2], primu_cuvant[letter1]

        # Construim prop actualizata

        for i in range(len(words)):
            if i == word1:
                updt_sentence += "".join(primu_cuvant)
            elif i == word2:
                updt_sentence += "".join(al_doilea_cuvant)
            else:
                updt_sentence += words[i]
            updt_sentence += " "

        updt_sentence = updt_sentence[:-1]
        return updt_sentence











