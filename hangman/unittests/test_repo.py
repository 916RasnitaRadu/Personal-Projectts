import unittest
from Repository.repo import Repository


class RepoTest(unittest.TestCase):
    def setUp(self) -> None:
        self._repo = Repository("test_sentences.txt")
        self._repo.clear()

    def tearDown(self) -> None:
        pass

    def test_empty_Repo(self):
        self.assertEqual(len(self._repo), 0)

    def test_save(self):
        sentence = "ana are mere"
        self._repo.save(sentence)
        self.assertEqual(len(self._repo), 1)

    def test_get_all(self):
        self._repo.save("manuela")
        self._repo.save("asfasdg")
        lista = self._repo.get_all()
        self.assertEqual(lista, ["manuela", "asfasdg"])

    def test_clear(self):
        self._repo.clear()
        self.assertEqual(self._repo.__len__(), 0)