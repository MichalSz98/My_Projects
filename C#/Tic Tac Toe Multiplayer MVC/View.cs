using System.Collections.Generic;
using System.Windows.Forms;

namespace TicTacToe_Game
{
    class View
    {
        private Model m;

        private Controller c;

        public View(Model m)
        {
            this.m = m;
        }

        public void addEventListener(Controller c)
        {
            this.c = c;
        }

        public void notifyChanges(Button btn,string change)
        {
            btn.Text = change;
            btn.Enabled = false;
        }

        public void disableButtons(List<Button> btns)
        {
            try
            {
                foreach (Button btn in btns)
                {
                    btn.Enabled = false;
                }
            }
            catch { }
        }

        public void myTurn(List<Button> btns)
        {
            foreach (Button item in btns)
            {
                if (item.Text != "")
                    item.Enabled = false;
                else
                    item.Enabled = true;
            }
        }

        public void opponentsTurn(List<Button> btns)
        {
            foreach (Button item in btns)
            {
                if (item.Enabled == true)
                    item.Enabled = false;
            }
        }

        public void indisableButtons(List<Button> btns)
        {
            try
            {
                foreach (Button btn in btns)
                {
                    btn.Enabled = true;
                }
            }
            catch { }
        }

        public bool ifAllButtonsHaveText(List<Button> btns)
        {
            int counter = 0;
            foreach (Button btn in btns)
            {
                if (btn.Text != "")
                    counter++;
            }
            if (counter == 9)
                return true;

            return false;
        }

        public void showWinner(string winner)
        {
            MessageBox.Show(winner + " wygrał!");
        }

        public void showNoOneWin()
        {
            MessageBox.Show("Brak zwycięzcy!");
        }

        public void newGame(List<Button> btns)
        {
            try
            {
                foreach (Button btn in btns)
                {
                    btn.Enabled = true;
                    btn.Text = "";
                }
            }
            catch { }
        }
    }
}
