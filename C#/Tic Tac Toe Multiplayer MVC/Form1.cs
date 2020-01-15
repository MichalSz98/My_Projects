using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace TicTacToe_Game
{
    public partial class Form1 : Form
    {
        private Model m;
        private View v;
        private Controller c;

        public Form1()
        {
            InitializeComponent();


            List<Button> buttons = new List<Button>();
            foreach (var btn in this.Controls.OfType<Button>())
                buttons.Add(btn);

            this.m = new Model(this);

            this.v = new View(m);
            m.registerView(v, buttons);

            this.c = new Controller(v, m);
            v.addEventListener(c);
        }

        public void preLoad()
        {
            c.startPreLoad();
        }

        public void setLastIndex(int index)
        {
            c.notifyLastIndexChanged(index);
        }

        public void setPlayerChar(string znak)
        {
            c.notifyPLayerCharChanged(znak);
        }

        private void btnClick(object sender, EventArgs e)
        {
            c.notifyBtnPressed(sender);
        }

        private void nowaGraToolStripMenuItem_Click(object sender, EventArgs e)
        {
            c.notifyToolStripMenuItemClicked(sender);
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            c.notifyDelete();
        }

        private void Form1_Shown_1(object sender, EventArgs e)
        {
            c.notifyWaiting();
        }

        private void Form1_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyValue == (char)Keys.R)
                c.notifyWaiting();
        }

        public void Form1_MouseMove(object sender, MouseEventArgs e)
        {
            c.notifyWaiting();
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
        }
    }

}
