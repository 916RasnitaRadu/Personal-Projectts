import unittest
from src.constants.constants import *


class ConstantsTest(unittest.TestCase):
    def setUp(self) -> None:
        self._player_piece = CONST_PLAYER_PIECE
        self._ai_piece = CONST_AI_PIECE
        self._empty = CONST_EMPTY
        self._row_count = CONST_ROW_COUNT
        self._column_count = CONST_COLUMN_COUNT
        self._player_turn = CONST_PLAYER_TURN
        self._ai_turn = CONST_AI_TURN

    def tearDown(self) -> None:
        pass

    def test_constants(self):
        self.assertEqual(self._player_piece, 1)
        self.assertEqual(self._ai_piece, 2)
        self.assertEqual(self._empty, 0)
        self.assertEqual(self._row_count, 6)
        self.assertEqual(self._column_count, 7)
        self.assertEqual(self._player_turn, 0)
        self.assertEqual(self._ai_turn, 1)