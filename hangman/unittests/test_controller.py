import unittest
from Controller.controller import Controller
from Repository.repo import Repository


class ControllerTest(unittest.TestCase):
    def setUp(self) -> None:
        self._repo = Repository("test_sentences.txt")
        self._repo.clear()
        self._control = Controller(self._repo)

    def tearDown(self) -> None:
        pass

    def test_save_to_repo(self):
        self._control.save_to_repo("vanila suciu")
        self.assertEqual(self._repo.__len__(), 1)

    def test_is_over1(self):
        self.assertTrue(self._control.is_over1("ana", "ana"))
        self.assertTrue(self._control.is_over1("the white fox jumps in the snow", "the white fox jumps in the snow"))
        self.assertFalse(self._control.is_over1("miguel", "nu e miguel"))

    def test_is_over2(self):
        hangman = "han____"
        self.assertTrue(self._control.is_over2("hangman"))
        self.assertFalse(self._control.is_over2(hangman))

    def test_get_set_of_known_words(self):
        words = ["ana", "has", "apples"]
        self.assertEqual(self._control.get_set_of_known_letter(words), {"a", "h", "s"})

    def test_make_sentence(self):
        unknown_sentence = "ana has apples"

        self.assertEqual(self._control.make_sentence(unknown_sentence), "a_a has a____s")

    def test_make_hangman(self):
        self.assertEqual(self._control.make_hangman(), "_______")

    def test_update_hangman(self):
        self.assertEqual(self._control.update_hangman(2, "ha_____"), "han____")

    def test_update_sentence(self):
        sentence = self._control.update_sentence("n", "a_a has a____s", "ana has apples")
        self.assertEqual(sentence, "ana has a____s")

