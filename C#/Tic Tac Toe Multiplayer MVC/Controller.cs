using System.Windows.Forms;

namespace TicTacToe_Game
{
    class Controller
    {
        private Model m;
        private View v;

        public Controller(View v,Model m)
        {
            this.v = v;
            this.m = m;
        }

        public void startPreLoad()
        {
            m.setPreLoad();
        }

        public void notifyWaiting()
        {
            m.waitForTurn();
        }

        public void notifyBtnPressed(object sender)
        {
            Button btn = (Button)sender;
            m.set(btn);
        }

        public void notifyToolStripMenuItemClicked(object sender)
        {
            ToolStripMenuItem tsmi = (ToolStripMenuItem)sender;
            m.setNewGame(tsmi);
        }

        public void notifyPLayerCharChanged(string znak)
        {
            m.setPlayerChar(znak);
        }

        public void notifyLastIndexChanged(int index)
        {
            m.setLastIndex(index);
        }

        public void notifyDelete()
        {
            m.deleteTableContent();
        }
    }
}
