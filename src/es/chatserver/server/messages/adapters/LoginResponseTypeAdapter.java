/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server.messages.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import es.chatserver.server.messages.responses.LoginResponse;
import java.io.IOException;

/**
 *
 * @author Adrián Fernández Cano
 */
public class LoginResponseTypeAdapter extends TypeAdapter<LoginResponse> {

    @Override
    public void write(JsonWriter out, final LoginResponse loginResponse) throws IOException {


    }

    @Override
    public LoginResponse read(JsonReader in) throws IOException {


        
        return new LoginResponse();
    }
    
}
