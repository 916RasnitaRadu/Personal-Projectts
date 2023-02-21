class Validator:
    def __init__(self):
        pass

    @staticmethod
    def validate(params_list, unkwn_sentence):
        if len(params_list) != 5 or params_list[2] != "-":
            return False
        try:
            word1 = int(params_list[0])
            letter1 = int(params_list[1])
            word2 = int(params_list[3])
            letter2 = int(params_list[4])

            words = unkwn_sentence.split()
            if word1 >= len(words) or word2 >= len(words):
                return False
            if letter1 >= len(words[word1]) or letter2 >= len(words[word2]):
                return False

            return True
        except ValueError:
            return False
