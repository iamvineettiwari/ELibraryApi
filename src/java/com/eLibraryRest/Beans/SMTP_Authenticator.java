/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest.Beans;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Manas
 */
public class SMTP_Authenticator extends Authenticator{
    private final PasswordAuthentication authentication;
        public SMTP_Authenticator(String login, String password){
            authentication = new PasswordAuthentication(login, password);
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication(){
            return authentication;
        }
}
