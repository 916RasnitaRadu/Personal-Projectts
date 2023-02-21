import unittest
from src.Controller.controller import Controller
from src.Repository.repo import Repository


class ControllerTest(unittest.TestCase):
    def setUp(self) -> None:
        self._repo = Repository("test_sentences.txt")
        self._controller = Controller(self._repo)

    def tearDown(self) -> None:
        pass

    def test_compute_score(self):
        score = self._controller.compute_score("Marean face tiktok")
        self.assertEqual(score, 16)
        score = self._controller.compute_score("Ana are mere")
        self.assertEqual(score, 10)

    def test_scramble_string(self):
        string = "wordisimo"
        self.assertTrue(string != self._controller.scramble_string(string))

    def test_pick_random(self):
        sentence = self._controller.pick_random()
        self.assertTrue(self._repo.find_sentence(sentence))

    def test_split_command(self):
        cmd_line = "swap 0 4 - 0 1"
        cmd_word, cmd_params = self._controller.split_command(cmd_line)
        self.assertEqual(cmd_word, "swap")
        self.assertEqual(cmd_params, "0 4 - 0 1")

    def test_split_params(self):
        cmd_params = "0 4 - 0 1"
        params_list = self._controller.split_params(cmd_params)
        self.assertEqual(params_list, ['0', '4', '-', '0', '1'])

    def test_swap_letters(self):
        unknwn_sentence = "ana are multe mere"
        word1 = 0
        word2 = 2
        letter1 = 0
        letter2 = 1
        new_sent = self._controller.swap_letters(word1, letter1, word2, letter2, unknwn_sentence)
        self.assertEqual(new_sent, "una are malte mere")