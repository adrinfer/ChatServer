/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.utils;

import java.text.SimpleDateFormat;

/**
 *
 * @author adrinfer
 */
public class Utils {
    
    
    //Obtener formato de fecha para los mensajes
    public SimpleDateFormat getDfMessage()
    {
        return new SimpleDateFormat("HH:mm:ss");
    }
    
}
