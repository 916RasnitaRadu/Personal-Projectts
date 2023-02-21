import pygame
from src.ui.ui import UI
from src.constants.constants import *
import random
import sys
import math


class GUI(UI):
    def __init__(self, board, game_handler, ai):
        super().__init__(board, game_handler, ai)
        pygame.init()
        self._width = CONST_COLUMN_COUNT * CONST_SQUARE_SIZE
        self._height = (CONST_ROW_COUNT + 1) * CONST_SQUARE_SIZE
        self._size = (self._width, self._height)
        self._screen = pygame.display.set_mode(self._size)
        self._font = pygame.font.SysFont("dejavuserif", 75)

    def draw_board(self):
        for c in range(CONST_COLUMN_COUNT):
            for r in range(CONST_ROW_COUNT):
                pygame.draw.rect(self._screen, BLUE, (c*CONST_SQUARE_SIZE, (r+1)*CONST_SQUARE_SIZE, CONST_SQUARE_SIZE, CONST_SQUARE_SIZE))
                pygame.draw.circle(self._screen, BLACK, (int(c*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2), int((r+1)*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2)), CONST_RADIUS)
        for c in range(CONST_COLUMN_COUNT):
            for r in range(CONST_ROW_COUNT):
                if self.board.get_elem(r, c) == 1:
                    pygame.draw.circle(self._screen, RED, (int(c*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2), self._height - int(r*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2)), CONST_RADIUS)
                elif self.board.get_elem(r, c) == 2:
                    pygame.draw.circle(self._screen, YELLOW, (int(c*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2), self._height - int(r*CONST_SQUARE_SIZE + CONST_SQUARE_SIZE/2)), CONST_RADIUS)

            pygame.display.update()

    def do_animations(self, event, turn):
        pygame.draw.rect(self._screen, BLACK, (0, 0, self._width, CONST_SQUARE_SIZE))
        pos_x = event.pos[0]
        if turn == CONST_PLAYER_TURN:
            pygame.draw.circle(self._screen, RED, (pos_x, CONST_SQUARE_SIZE // 2), CONST_RADIUS)
        elif turn == CONST_AI_TURN:
            pygame.draw.circle(self._screen, YELLOW, (pos_x, CONST_SQUARE_SIZE // 2), CONST_RADIUS)
        pygame.display.update()

    def gui_start(self):
        game_over = False
        choice = [0, 1]
        turn = random.choice(choice)
        self.draw_board()
        pygame.display.update()
        if self._ai is None:
            while not game_over:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        sys.exit()
                    elif event.type == pygame.MOUSEMOTION:
                        self.do_animations(event, turn)
                    elif event.type == pygame.MOUSEBUTTONDOWN:
                        pygame.draw.rect(self._screen, BLACK, (0, 0, self._width, CONST_SQUARE_SIZE))
                        if turn == CONST_PLAYER_TURN:
                            pos_x = event.pos[0]
                            col = pos_x // CONST_SQUARE_SIZE
                            if self.ui_is_valid_location(col):
                                piece = CONST_PLAYER_PIECE
                                self.ui_drop_piece(col, piece)

                                if self.ui_is_winning(piece):
                                    print("PLAYER 1 WINS! CONGRATS!")
                                    label = self._font.render("PLAYER 1 WINS!", True, RED)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                                if self.ui_is_winning(piece) == 5:
                                    print("REMIZA FRAIERILOR!")
                                    label = self._font.render("REMIZA!", True, BLUE)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                        else:
                            pos_x = event.pos[0]
                            col = pos_x // CONST_SQUARE_SIZE
                            if self.ui_is_valid_location(col):
                                piece = CONST_AI_PIECE
                                self.ui_drop_piece(col, piece)

                                if self.ui_is_winning(piece):
                                    print("PLAYER 2 WINS! CONGRATS!")
                                    label = self._font.render("PLAYER 2 WINS!", True, YELLOW)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                                if self.ui_is_winning(piece) == 5:
                                    print("REMIZA FRAIERILOR!")
                                    label = self._font.render("REMIZA!", True, BLUE)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                        turn += 1
                        turn = turn % 2
                        self.print_board()
                        self.draw_board()

                    if game_over:
                        pygame.time.wait(3000)
            pygame.quit()
        else:
            while not game_over:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        sys.exit()
                    elif event.type == pygame.MOUSEMOTION:
                        self.do_animations(event, turn)
                    elif event.type == pygame.MOUSEBUTTONDOWN:
                        pygame.draw.rect(self._screen, BLACK, (0, 0, self._width, CONST_SQUARE_SIZE))
                        if turn == CONST_PLAYER_TURN:
                            pos_x = event.pos[0]
                            col = pos_x // CONST_SQUARE_SIZE
                            if self.ui_is_valid_location(col):
                                piece = CONST_PLAYER_PIECE
                                self.ui_drop_piece(col, piece)

                                if self.ui_is_winning(piece):
                                    print("PLAYER 1 WINS! CONGRATS!")
                                    label = self._font.render("PLAYER 1 WINS!", True, RED)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                                if self.ui_is_winning(piece) == 5:
                                    print("REMIZA FRAIERILOR!")
                                    label = self._font.render("REMIZA!", True, BLUE)
                                    self._screen.blit(label, (40, 10))
                                    game_over = True
                                turn += 1
                                turn = turn % 2
                                self.print_board()
                                self.draw_board()
                if turn == CONST_AI_TURN and not game_over:
                    print("The computer picked: ")
                    board = self.board.__copy__
                    col, minimax_score = self._ai.minimax(board, 5, -math.inf, math.inf, True)
                    if self.ui_is_valid_location(col):
                        piece = CONST_AI_PIECE
                        self.ui_drop_piece(col, piece)

                        if self.ui_is_winning(piece):
                            print("C O M P U T E R   W I N S! L O S E R!")
                            label = self._font.render("COMPUTER WINS!", True, YELLOW)
                            self._screen.blit(label, (40, 10))
                            game_over = True
                        if self.ui_is_winning(piece) == 5:
                            print("REMIZA FRAIERILOR!")
                            label = self._font.render("REMIZA!", True, BLUE)
                            self._screen.blit(label, (40, 10))
                            game_over = True
                    turn += 1
                    turn = turn % 2
                    self.print_board()
                    self.draw_board()

                    if game_over:
                        pygame.time.wait(3000)
            pygame.quit()

