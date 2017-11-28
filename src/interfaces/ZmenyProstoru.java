/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author Matěj Dusbaba
 */
public interface ZmenyProstoru {
    public void registraceObserver(ObserverProstory observer);
    
    public void odebraniObserver(ObserverProstory observer);
    
    public void upozorniPozorovatele();
}
