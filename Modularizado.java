package enviarmail;
import java.util.Scanner;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Modularizado {
 /**
 *
 * @author Samuel --->starsaminf
 */

    public static String menu =
            "<--------INGRESE UNA OPCION-------->\n\n\n"
            + "          0 HOTMAIL \n"
            + "          1 GMAIL \n"
            + "          2 LOGUEO \n"
            + "          3 ESCRIBIR Y ENVIAR \n"
            + "          4 SALIR \n\n\n";

    public static void main(String[] args) throws MessagingException {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        String mensaje = "";
        String coreo = "", pass = "", destino = "";
        boolean verif = true;
        Transport t = null;
        Session session = null;
        Properties props = new Properties();
        int SeleCo = 10;
        //0 hotmail
        //1 gmail
        //menu
        boolean op = true;
        while (op) {
            System.err.println(menu);
            System.err.println("OPCION  ?");
            int option = sc2.nextInt();
            switch (option) {

                case 0:
                    SeleCo = 0;
                    break;
                case 1:
                    SeleCo = 1;
                    break;
                case 2:
                    if (SeleCo != 10) {
                        if (SeleCo == 0) {
                            props.setProperty("mail.smtp.host", "smtp.live.com");
                            props.setProperty("mail.smtp.starttls.enable", "true");
                            props.setProperty("mail.smtp.port", "25");
                            props.setProperty("mail.smtp.user", "STARSAMINF");
                            props.setProperty("mail.smtp.auth", "true");
                            session = Session.getDefaultInstance(props);
                            //LOGUEO 
                            System.err.println("TU NICK ");
                            coreo = sc.next();
                            System.err.println("TU PASS ");
                            pass = sc.next();
                            //FIN LOGUEO
                            //VERIFICAR
                            try {
                                t = session.getTransport("smtp");
                                if (logueo(t, coreo, pass)) {
                                    System.out.println("LOGUEO CORRECTO  ");
                                } else {
                                    verif = false;
                                }

                            } catch (NoSuchProviderException ex) {
                                Logger.getLogger(Modularizado.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //FIN VERIFICAR
                        } else {
                            props.setProperty("mail.smtp.host", "smtp.gmail.com");
                            props.setProperty("mail.smtp.starttls.enable", "true");
                            props.setProperty("mail.smtp.port", "587");
                            props.setProperty("mail.smtp.user", "STARSAMINF");
                            props.setProperty("mail.smtp.auth", "true");
                            session = Session.getDefaultInstance(props);
                            //LOGUEO 
                            System.err.println("TU NICK ");
                            coreo = sc.next();
                            System.err.println("TU PASS ");
                            pass = sc.next();
                            //FIN LOGUEO
                            //VERIFICAR

                            try {
                                t = session.getTransport("smtp");
                                if (logueo(t, coreo, pass)) {
                                    System.err.println("LOGUEO CORRECTO  ");
                                } else {
                                    verif = false;
                                }
                            } catch (NoSuchProviderException ex) {
                                Logger.getLogger(Modularizado.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //FIN VERIFICAR
                        }
                    } else {
                        System.err.println("SELECCIONE GMAIL O HOTMAIL");
                    }
                    break;

                case 3:
                    Scanner in = new Scanner(System.in);
                    Scanner ini = new Scanner(System.in);
                    if (verif) {
                        // Session session = Session.getDefaultInstance(props);
                        System.err.println("COREO DESTINO : ");
                        destino = in.next();
                        System.err.println("MENSAJE A ENVIAR  : ");
                        mensaje = ini.nextLine();

                        System.err.println("ENVIANDO ....  : ");
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(coreo));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
                        message.setSubject("Prueba de envio de mensajes by starsaminf");
                        message.setText(mensaje+" \n by zam \n zamuel01@gmail.com\n jeje");
                        //String coreo,String destino,String pas,String mensaje,Properties props
                        if (enviar2(t, session, coreo, message)) {
                            System.out.println("ENVIADO CORRECTAMENTE  ");
                        } else {
                            System.out.println("LOGUEO MALO ");
                        }
                    }

                    break;

                //String coreo,String destino,String pas,String mensaje,Properties props
                default:
                    op = false;
                    break;
            }

        }
//fin menu
    }

    ///verifica si el correo y pas son correctos
    public static boolean logueo(Transport t, String coreo, String pas) {
        try {
            t.connect(coreo, pas);
            return true;
        } catch (MessagingException ex) {
            System.err.println("CONTRASEÑA MALA   ");
            return false;
        }
    }
    //fin verifica correo y pas 

    public static boolean enviar2(Transport t, Session session, String coreo, MimeMessage message) throws MessagingException {
        try {
            t.sendMessage(message, message.getAllRecipients());
            try {
                t.close();
            } catch (MessagingException ex) {
                Logger.getLogger(Modularizado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Modularizado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}