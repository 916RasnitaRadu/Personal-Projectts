import unittest
from src.Validator.validator import Validator


class ValidatorTest(unittest.TestCase):
    def setUp(self) -> None:
        self._valid = Validator()

    def tearDown(self) -> None:
        pass

    def test_validate(self):
        params_list = ['0', '1', '-', '2', '0']
        unknwn_sentence = "Ana are multe mere"
        self.assertTrue(self._valid.validate(params_list, unknwn_sentence))

        params_list = ['0', '1', 'abc', '9494', '0']
        unknwn_sentence = "Ana are multe mere"
        self.assertFalse(self._valid.validate(params_list, unknwn_sentence))