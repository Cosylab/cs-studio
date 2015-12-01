package org.csstudio.email;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaxMailSender {

    private String host, from, username, password;
    private List<String> to, cc, cci;
    private String subject = "";
    private String body = "";
    private int port;
    private boolean auth;
    private boolean startTLS;
    private boolean ssl;

    public JavaxMailSender() {
        this.host = Preferences.getSMTP_Host();
        this.port = Preferences.getSMTP_Port();
        this.auth = Preferences.isSMTPAuthEnabled();
        this.startTLS = Preferences.isSMTPStartTLSEnabled();
        this.from = Preferences.getSMTP_Sender();
        this.username = Preferences.getSMTP_Username();
        this.password = Preferences.getSMTP_Password();
        this.ssl = Preferences.isSMTPSSL();
    }

    public void send() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", String.valueOf(auth));
        props.put("mail.smtp.starttls.enable", String.valueOf(startTLS));
        if (ssl) {
            props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        }
        Session session;
        if (auth) {
            session = Session.getDefaultInstance(props,new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            session = Session.getDefaultInstance(props);
        }
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            Address[] toAddresses = new Address[to.size()];
            int count = 0;
            for (String address : to) {
                toAddresses[count] = new InternetAddress(address);
                count++;
            }
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            if (cc != null) {
                count = 0;
                Address[] ccAddresses = new Address[cc.size()];
                for (String address : cc) {
                    ccAddresses[count] = new InternetAddress(address);
                    count++;
                }
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }
            if (cci != null) {
                count = 0;
                Address[] bccAddresses = new Address[cci.size()];
                for (String address : cci) {
                    bccAddresses[count] = new InternetAddress(address);
                    count++;
                }
                message.setRecipients(Message.RecipientType.BCC, bccAddresses);
            }
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            Activator.getLogger().log(Level.FINE, "Sent EMail to {0}", to);
        } catch (MessagingException e) {
            Activator.getLogger().log(Level.SEVERE,
                    "Exception during EMail sending: {0}", e.getMessage());
        }
        System.out.println("send");
    }

    public boolean checkContent() {
        return ((to != null && !to.isEmpty())
                && (subject != null && !"".equals(subject.trim()))
                && (body != null));
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public List<String> getTo() {
        return to;
    }
    public void setTo(List<String> to) {
        this.to = to;
    }
    public List<String> getCc() {
        return cc;
    }
    public void setCc(List<String> cc) {
        this.cc = cc;
    }
    public List<String> getCci() {
        return cci;
    }
    public void setCci(List<String> cci) {
        this.cci = cci;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        if (subject == null) return;
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        if (body == null) return;
        this.body = body;
    }

}
