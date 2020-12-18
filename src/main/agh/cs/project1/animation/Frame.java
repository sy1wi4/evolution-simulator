package agh.cs.project1.animation;
import javax.swing.JFrame;


public class Frame extends JFrame{

        public Frame() {
            super("Evolution Simulator");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exit app after clicking the close button
            setSize(1500,1000);  // ratio: width/height = 3/2
            setLocationRelativeTo(null);  // centering
        }
}
