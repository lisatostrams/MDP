/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.ai.vroon.mdp;

/**
 *
 * @author Lisa Tostrams s4386167
 * class to contain tuple for experience
 * public attributes.. sorry
 */
public class Experience {
    int x;
    int y; 
    Action a; 
    double r; 
    int x_new;
    int y_new; 
    public Experience(int x, int y, Action a, double r, int x_new, int y_new) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.r = r;
        this.x_new = x_new;
        this.y_new = y_new; 
        
    }
    
}
