import unittest
from Repository.repo import Repository
from Validator.validator import Validator


class ValidatorTest(unittest.TestCase):
    def setUp(self) -> None:
        self._repo = Repository("test_sentences.txt")
        self._repo.save("ana are mere")

    def tearDown(self) -> None:
        pass

    def test_validate(self):
        invalid = Validator(self._repo)
        invalid2 = Validator(self._repo)
        valid = Validator(self._repo)

        self.assertFalse(valid.validate("ana are mere"))
        self.assertFalse(invalid.validate("masas f sdfdsfs"))
        self.assertTrue(invalid2.validate("MERG LAoaf MARE JANI"))
